package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.entity.User;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddUserCommand extends Command{

    private static final Logger LOG = Logger.getLogger(AddUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        User user = new User(login,email,password,firstName,lastName,roleId);
        LOG.info("User added with email="+email);

        String forward = Path.PAGE_ADD_USER;

        boolean added = DBManager.getInstance().insertUser(user);

        if (added){
            forward = Path.PAGE_INFO_USERS;
            List<User> users = DBManager.getInstance().getAllUser();
            request.setAttribute("listUser", users);
            LOG.info("listUsers ==> " + users);
            LOG.debug("Get list users finish");
        }

        return forward;
    }
}
