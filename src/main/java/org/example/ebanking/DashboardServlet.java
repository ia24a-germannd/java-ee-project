package org.example.ebanking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DashboardServlet: doGet started");

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        System.out.println("UserID from session: " + userId);

        if (userId != null) {
            try {
                List<Account> accounts = accountDAO.getAccountsByUserId(userId);
                System.out.println("Number of accounts found: " + accounts.size());

                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

            } catch (Exception e) {
                System.out.println("Error in DashboardServlet: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            System.out.println("No userId found in session");
            response.sendRedirect("index.jsp?error=No-userId-found-in-session");
        }
    }
}