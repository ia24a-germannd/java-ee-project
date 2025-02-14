package org.example.ebanking;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        if (userId != -1) {
            String accountNumber = generateRandomAccountNumber();
            userDAO.createAccount(userId, accountNumber);
            response.sendRedirect("dashboard.jsp?success=Account-created-successfully");
        } else {
            response.sendRedirect("register.jsp?error=Registration-failed");
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