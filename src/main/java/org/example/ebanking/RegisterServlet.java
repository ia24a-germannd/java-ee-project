package org.example.ebanking;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String email = request.getParameter("email");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=Passwords-do-not-match");
            return;
        }

        try (Connection con = DBConnector.getConnection()) {
            if (con == null) {
                response.sendRedirect("register.jsp?error=Database-connection-failed");
                return;
            }

            String query = "INSERT INTO Users (username, password, email) VALUES (?, ?,?);";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("index.jsp?success=Account-created-successfully");
            } else {
                response.sendRedirect("register.jsp?error=Registration-failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Database error");
        }
    }
}
