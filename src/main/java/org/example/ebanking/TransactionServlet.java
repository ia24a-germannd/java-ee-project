package org.example.ebanking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
    private TransactionDAO transactionDAO = new TransactionDAO();
    private AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountIdStr = request.getParameter("accountId");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int accountId = Integer.parseInt(accountIdStr);

            List<Account> userAccounts = accountDAO.getAccountsByUserId(userId);

            Account targetAccount = null;
            for (Account account : userAccounts) {
                if (account.getAccountId() == accountId) {
                    targetAccount = account;
                    break;
                }
            }

            if (targetAccount == null) {
                response.sendRedirect("dashboard?error=unauthorized-access");
                return;
            }

            List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(accountId);

            request.setAttribute("transactions", transactions);
            request.setAttribute("accountNumber", targetAccount.getAccountNumber());

            request.getRequestDispatcher("/transaction.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("error?error=invalid-account");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error?error=server-error");
        }
    }
}