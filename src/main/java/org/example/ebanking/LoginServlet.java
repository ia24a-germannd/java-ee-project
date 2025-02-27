package org.example.ebanking;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        accountDAO = new AccountDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        int userId = userDAO.getUserId(username, password);

        if (userId != -1) {
            int accountId = accountDAO.getAccountIdByUserId(userId);

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("userId", userId);
            session.setAttribute("accountId", accountId);


            if (rememberMe != null) {
                Cookie userCookie = new Cookie("username", username);
                userCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(userCookie);
            }

            response.sendRedirect("dashboard");
        } else {
            response.sendRedirect("login?error=Invalid-credentials");
        }
    }
}