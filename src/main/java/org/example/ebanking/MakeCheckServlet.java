package org.example.ebanking;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.io.File;

@WebServlet("/makeChecks")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class MakeCheckServlet extends HttpServlet {
    private CheckDAO checkDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private static final String UPLOAD_DIRECTORY = "signatures";
    private static final float FLAG_THRESHOLD = 5000.0f;

    @Override
    public void init() throws ServletException {
        checkDAO = new CheckDAO();
        accountDAO = new AccountDAO();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            HttpSession session = request.getSession();
            Integer accountId = (Integer) session.getAttribute("accountId");

            if (accountId == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            String recipientName = request.getParameter("recipientName");
            float amount;
            try {
                amount = Float.parseFloat(request.getParameter("amount"));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid amount format");
                request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
                return;
            }

            String note = request.getParameter("note");

            Part filePart = request.getPart("signature");
            String fileName = null;
            if (filePart != null) {
                fileName = getSubmittedFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                fileName = System.currentTimeMillis() + "_" + fileName;
                filePart.write(uploadPath + File.separator + fileName);
            }

            if (recipientName == null || recipientName.trim().isEmpty() ||
                    amount <= 0 || fileName == null) {
                request.setAttribute("error", "Please fill in all required fields with valid values");
                request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
                return;
            }

            Account account = accountDAO.getAccount(accountId);
            if (account == null || account.getBalance() < amount) {
                request.setAttribute("error", "Insufficient funds");
                request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
                return;
            }

            conn = DBConnector.getConnection(); // Get connection with autoCommit false

            // Create check object
            Check check = new Check();
            check.setAccountId(accountId);
            check.setRecipientName(recipientName);
            check.setAmount(amount);
            check.setCheckDate(new Date(System.currentTimeMillis()));
            check.setNote(note);
            check.setSignatureFile(UPLOAD_DIRECTORY + File.separator + fileName);

            // Create transaction object
            Transaction transaction = new Transaction();
            transaction.setAccountId(accountId);
            transaction.setAmount(-amount); // Negative amount for check payments
            transaction.setRecipient(recipientName);
            transaction.setFlagged(amount >= FLAG_THRESHOLD);

            boolean checkCreated = checkDAO.createCheck(check);
            if (checkCreated) {
                float newBalance = account.getBalance() - amount;
                account.setBalance(newBalance);

                boolean balanceUpdated = accountDAO.updateBalance(account);
                boolean transactionCreated = transactionDAO.createTransaction(transaction);

                if (balanceUpdated && transactionCreated) {
                    conn.commit();
                    session.setAttribute("balance", newBalance);
                    response.sendRedirect("dashboard");
                } else {
                    conn.rollback();
                    request.setAttribute("error", "Failed to process the check");
                    request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
                }
            } else {
                conn.rollback();
                request.setAttribute("error", "Failed to create check");
                request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.out.println("Error in MakeCheckServlet: " + e.getMessage());
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("makeCheck.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}