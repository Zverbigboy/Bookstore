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

public class UpdateBookCommand  extends Command{

    private static final long serialVersionUID = 7118987897987823L;

    private static final Logger LOG = Logger.getLogger(UpdateBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {

        LOG.debug("UpdateBookCommand start");
        response.setCharacterEncoding("UTF-8");
        DBManager dbManager = DBManager.getInstance();

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String editon = request.getParameter("editon");
        Date dateEditon = Date.valueOf(request.getParameter("dateEditon"));
        Integer numberCopies = Integer.valueOf(request.getParameter("numberCopies"));

        Book newBook = new Book(id, title, author, editon,dateEditon, numberCopies);

        boolean isUpdate = dbManager.updateBook(newBook);
        LOG.info("Insert book into db ==> " + isUpdate);

        List<Book> listBook = dbManager.listAllBooks();
        request.setAttribute("listBook", listBook);
        LOG.info("listBook ==> " + listBook);
        LOG.debug("AddBookCommand starts");

        String forward = Path.COMMAND_BOOK_LIST;

        return forward;

    }
}
