package com.s3k3l3v.bookstore.controller.command;


import com.s3k3l3v.bookstore.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class Command implements Serializable{

    private static final long serialVersionUID = 7988987897987878L;

    public abstract String execute(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException, ServletException, DBException, SQLException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
