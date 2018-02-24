package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Book;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListBookCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger LOG = Logger.getLogger(ListBookCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {

        LOG.debug("Command starts");
        response.setCharacterEncoding("UTF-8");
        List<Book> books = DBManager.getInstance().listAllBooks();
        LOG.trace("Found in DB: listBook --> " + books);

        Collections.sort(books, new Comparator<Book>() {
            public int compare(Book o1, Book o2) {
                return  o1.getId() - o2.getId();
            }
        });

        request.setAttribute("listBook", books);
        LOG.trace("Set the request attribute: listBook ==> " + books);

        LOG.debug("Command finished");
        return Path.PAGE_LIST_BOOK;
    }
}
