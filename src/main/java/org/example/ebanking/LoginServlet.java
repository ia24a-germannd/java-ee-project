package org.example.ebanking;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        try (Connection con = DBConnector.getConnection()) {
            if (con == null) {
                response.sendRedirect("login.jsp?error=Database connection failed");
                return;
            }

            String query = "SELECT * FROM Users WHERE username=? AND password=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                if (rememberMe != null) {
                    Cookie userCookie = new Cookie("username", username);
                    userCookie.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(userCookie);
                }

                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("register.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Database error");
        }
    }
}