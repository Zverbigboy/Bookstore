package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.entity.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand extends  Command{
    private static final long serialVersionUID = 7118987897987877L;

    private static final Logger logger = Logger.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Command starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        logger.error("Set the request attribute: errorMessage ==> " + errorMessage);

        logger.debug("command finished");

        return Path.PAGE_ERROR_PAGE;
    }
}
