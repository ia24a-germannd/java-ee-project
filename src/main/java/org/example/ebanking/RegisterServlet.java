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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register?error=Passwords-do-not-match");
            return;
        }

        int existingUserId = userDAO.getUserId(username, password);
        if (existingUserId != -1) {
            response.sendRedirect("login?error=User-already-exists");
            return;
        }

        int userId = userDAO.registerUser(username, password, email);

        if (userId != -1) {
            String accountNumberOne = generateRandomAccountNumber();
            String accountNumberTwo = generateRandomAccountNumber();

            boolean mainAccountCreated = userDAO.createAccount(userId, accountNumberOne, "Main Account");
            boolean savingsAccountCreated = userDAO.createAccount(userId, accountNumberTwo, "Savings Account");

            if (mainAccountCreated && savingsAccountCreated) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("userId", userId);

                if (firstName != null && !firstName.isEmpty()) {
                    session.setAttribute("firstName", firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    session.setAttribute("lastName", lastName);
                }

                session.setAttribute("email", email);
                session.setAttribute("welcomeMessage", "Welcome to E-Banking, " + username + "!");

                response.sendRedirect("dashboard");
            } else {
                response.sendRedirect("error?error=Account-creation-failed");
            }
        } else {
            response.sendRedirect("error?error=Registration-failed");
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