package org.example.ebanking;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        int userId = userDAO.getUserId(username, password);

        if (userId != -1) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user_id", userId);

            if (rememberMe != null) {
                Cookie userCookie = new Cookie("username", username);
                userCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(userCookie);
            }

            int userInfo = userDAO.getUserId(username, password);
            if (userInfo == -1) {
                String accountNumberOne = generateRandomAccountNumber();
                String accountNumberTwo = generateRandomAccountNumber();
                userDAO.createAccount(userId, accountNumberOne, "Main Account");
                userDAO.createAccount(userId, accountNumberTwo, "Savings Account");
            }

            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("register.jsp");
        }
    }

    private String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}