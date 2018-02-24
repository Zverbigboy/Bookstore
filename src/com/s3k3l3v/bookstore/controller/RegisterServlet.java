package com.s3k3l3v.bookstore.controller;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "Register", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(RegisterServlet.class);


    private DBManager dbManager ;//= new DBManager();

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

//        bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
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
        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String roleId = request.getParameter("roleId");
        String errorMsg = null;
        if(email == null || email.equals("")){
            errorMsg = "Email can't be null or empty.";
        }
        if(password == null || password.equals("")){
            errorMsg = "Password can't be null or empty.";
        }
        if(login == null || login.equals("")){
            errorMsg = "Login can't be null or empty.";
        }
        if(firstName == null || firstName.equals("")){
            errorMsg = "First Name can't be null or empty.";
        }
        if(lastName == null || lastName.equals("")){
            errorMsg = "Last Name can't be null or empty.";
        }

        if(errorMsg != null){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        }else{

            Connection con = null;
            try {
                con = dbManager.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DBException e) {
                e.printStackTrace();
            }
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement("insert into users (login,email, password,first_name, last_name, role_id) values (?,?,?,?,?,?)");
                ps.setString(1, login);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, firstName);
                ps.setString(5, lastName);
                System.out.println(roleId);
                ps.setInt(6, Integer.parseInt(roleId));

                ps.executeUpdate();

                logger.info("User registered with email="+email);

                //forward to login page to login
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
                PrintWriter out= response.getWriter();
                out.println("<font color=green>Registration successful, please login below.</font>");
                rd.include(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Database connection problem");
                throw new ServletException("DB Connection problem.");
            }finally{
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("SQLException in closing PreparedStatement");
                }
            }
        }

    }
}
