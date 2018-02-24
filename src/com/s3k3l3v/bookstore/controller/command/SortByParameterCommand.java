package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class SortByParameterCommand extends Command{

    private static final Logger LOG = Logger.getLogger(SortByParameterCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        DBManager manager = DBManager.getInstance();



        return null;
    }
}
