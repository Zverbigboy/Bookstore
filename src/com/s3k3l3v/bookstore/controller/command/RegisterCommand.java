package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.entity.User;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RegisterCommand extends Command{

    private static final long serialVersionUID = 7118987897987111L;

    private static final Logger logger = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        response.setCharacterEncoding("UTF-8");
        DBManager manager = DBManager.getInstance();

        String forward = Path.PAGE_REGISTER;

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int roleId = Integer.parseInt(request.getParameter("roleId"));
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
            System.out.println("asd");
//            request.setAttribute("errorMail", "Email enter:  " + email +" check your valid email");
//            request.setAttribute("errorPassword", "Password enter:  " + password +" check your password valid");
//            request.setAttribute("errorLogin", "Login enter:  " + login +" check your login valid");

            RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/register.jsp");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        }else {
            User user = new User(login, email, password, firstName, lastName, roleId);
            logger.info("User registered with email=" + email);

            forward = Path.PAGE_REGISTER;


            boolean added = manager.insertUser(user);

            if (added) {
                forward = Path.PAGE_LOGIN;
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/login.jsp");
                PrintWriter out = response.getWriter();
                out.println("<font color=green>Registration successful, please login below.</font>");
                rd.include(request, response);
                return forward;
            }
        }
        return forward;

    }
}
