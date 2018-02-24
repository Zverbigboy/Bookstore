package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.entity.Role;
import com.s3k3l3v.bookstore.entity.User;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommand extends Command {

    private static final long serialVersionUID = 7118987897987876L;

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        logger.debug("Command starts");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        String forward = Path.PAGE_ERROR_PAGE;

        // obtain email and password from a request
        DBManager manager = DBManager.getInstance();
        String email = request.getParameter("email");
        logger.trace("Request parameter: email --> " + email);

        String password = request.getParameter("password");

//        if (email != )

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {

            throw new DBException("Login/password cannot be empty");
        }

        User user = manager.findUserByEmail(email);
        logger.trace("Found in DB: user --> " + user);

        if (user == null || !email.equals(user.getEmail()) ){
            throw new DBException("Cannot find user with such email: " + email +" check your enter email");
        }

        if (user == null || !password.equals(user.getPassword())) {
            throw new DBException("Cannot find user with such password, please check your password");
        }

        Role userRole = Role.getRole(user);
        logger.trace("userRole --> " + userRole);


        if (userRole == Role.ADMIN) {
            forward = Path.COMMAND_BOOK_LIST;
        }

        if (userRole == Role.CLIENT) {
            request.setAttribute("listAllBook", manager.listAllBooks());
            forward = Path.COMMAND_BOOK_LIST_CLIENT;
        }

        if (userRole == Role.LIBRARIAN) {
            forward = Path.COMMAND_ORDER_BOOK_LIST_FOR_LIBRARIAN;
        }

        session.setAttribute("user", user);
        logger.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        logger.trace("Set the session attribute: userRole --> " + userRole);

        logger.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        logger.debug("Command finished");


        System.out.println(request.getServletContext().getRealPath(request.getServletPath()));

        return forward;
    }

}

