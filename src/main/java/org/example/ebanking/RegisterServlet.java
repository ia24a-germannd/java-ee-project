package org.example.ebanking;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String email = request.getParameter("email");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=Passwords-do-not-match");
            return;
        }

        int userId = userDAO.registerUser(username, password, email);

        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("user_id", userId);

        if (userId != -1) {
            String accountNumberOne = generateRandomAccountNumber();
            String accountNumberTwo = generateRandomAccountNumber();
            userDAO.createAccount(userId, accountNumberOne, "Main Account");
            userDAO.createAccount(userId, accountNumberTwo, "Savings Account");
            response.sendRedirect("dashboard.jsp?success=Account-created-successfully");
        } else {
            response.sendRedirect("error.jsp?error=Registration-failed");
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