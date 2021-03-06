package com.s3k3l3v.bookstore.controller;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Role;
import com.s3k3l3v.bookstore.entity.User;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(String.valueOf(LoginServlet.class));

    private DBManager dbManager;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

//        dbManager = new DBManager(jdbcURL,jdbcUsername,jdbcPassword);
        try {
            dbManager = new DBManager();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorMsg = null;
        if (email == null || email.equals("")) {
            errorMsg = "User Email can't be null or empty";
        }
        if (password == null || password.equals("")) {
            errorMsg = "Password can't be null or empty";
        }

        if (errorMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        } else if (password != "") {

            try {
                User user = dbManager.findUserByEmail(email);
                logger.info("User found with details=" + user);
                Role role = Role.getRole(user);
                logger.trace("role " + role);

                System.out.println(password);
                System.out.println(user.getRoleId());
                System.out.println(role);

                if (role == Role.ADMIN) {
                    response.sendRedirect("/controller");
                }
                if (role == Role.CLIENT) {
                    response.sendRedirect("/pages/new");
                }
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                logger.trace("set the session attribute: user ==> " + user);
                session.setAttribute("role", role);
                logger.trace("set the session attribute: role ==> " + role);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = response.getWriter();
            logger.error("User not found with email=" + email);
            out.println("<font color=red>No user found with given email id, please register first.</font>");
            rd.include(request, response);
        }
    }

}
