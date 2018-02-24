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
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AddBookCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        LOG.debug("AddBookCommand starts");

        DBManager dbManager = DBManager.getInstance();

        response.setContentType("text/html; charset=UTF-8");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String editon = request.getParameter("editon");
        Date dateEditon = Date.valueOf(request.getParameter("dateEditon"));
        Integer numberCopies = Integer.valueOf(request.getParameter("numberCopies"));


        Book newBook = new Book(title, author, editon,dateEditon, numberCopies);

        LOG.info("Book before call insertBook() ==> " + newBook);
        LOG.info("run insertBook() ==> " + dbManager.insertBook(newBook));

        List<Book> listBook = dbManager.listAllBooks();
        request.setAttribute("listBook", listBook);

        LOG.info("listBook ==> " + listBook);
        LOG.debug("AddBookCommand starts");

        return Path.PAGE_LIST_BOOK;
    }
}
