package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Book;
import com.s3k3l3v.bookstore.entity.BookItemList;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListBookClientCommand extends Command{

    private static final long serialVersionUID = 7732286214029478113L;

    private static final Logger LOG = Logger.getLogger(ListBookClientCommand.class);

    private DBManager dbManager = null;

    private static class CompareByTitle implements Comparator<Book>, Serializable {
        private static final long serialVersionUID = -1753481565177573283L;

        @Override
        public int compare(Book o1, Book o2) {
            if (o1.getTitle().charAt(0) > o2.getTitle().charAt(0)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByAuthor implements Comparator<Book>, Serializable {
        private static final long serialVersionUID = -1753481565177573283L;

        @Override
        public int compare(Book o1, Book o2) {
            if (o1.getAuthor().charAt(0) > o2.getAuthor().charAt(0)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class CompareByEditon implements Comparator<Book>, Serializable {
        private static final long serialVersionUID = -1753481565177573283L;

        @Override
        public int compare(Book o1, Book o2) {
            if (o1.getEditon().charAt(0) > o2.getEditon().charAt(0)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByDateEditon implements Comparator<Book>, Serializable {
        private static final long serialVersionUID = -1753481565177573283L;

        @Override
        public int compare(Book o1, Book o2) {
            if (o1.getDateEditon().getTime() > o2.getDateEditon().getTime()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static Comparator<Book> compareByTitle = new CompareByTitle();
    private static Comparator<Book> compareByAuthor = new CompareByAuthor();
    private static Comparator<Book> compareByEditon = new CompareByEditon();
    private static Comparator<Book> compareByDateEditon = new CompareByDateEditon();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {

        LOG.debug("Command client catalog starts");

        String forward = Path.PAGE_BOOK_LIST_CLIENT;

        dbManager = DBManager.getInstance();
        List<Book> books = dbManager.listAllBooks();

        LOG.trace("Found in DB: listBook --> " + books);

        if (request.getParameter("sortByTitle") != null){
            Collections.sort(books, compareByTitle);
        }
        if (request.getParameter("sortByAuthor") != null){
            Collections.sort(books, compareByAuthor);
        }
        if (request.getParameter("sortByEditon") != null){
            Collections.sort(books, compareByEditon);
        }
        if (request.getParameter("sortByDateEditon") != null){
            Collections.sort(books, compareByDateEditon);
        }

//        Collections.sort(books, new Comparator<Book>() {
//            public int compare(Book o1, Book o2) {
//                return  o1.getId() - o2.getId();
//            }
//        });

        if (request.getParameter("myOrder") != null){
            forward = Path.COMMAND_ORDER_BOOK_LIST;
        }

        request.setAttribute("listBookClient", books);

        LOG.trace("Set the request attribute: listBookClient ==> " + books);

        LOG.debug("Command client catalog finished");


        if (request.getParameter("search") != null) {
            forward = getParam(request);
        }
        if (request.getParameter("searchByAuthor") != null) {
            forward = getParam(request);
        }

        HttpSession session = request.getSession(false); // check if session exists
        if (session != null) {
            BookItemList book;
            synchronized (session) {
                // Retrieve the shopping book for this session, if any. Otherwise, create one.
                book = (BookItemList) session.getAttribute("book");
                if (book != null && !book.isEmpty()) {
                    forward = Path.PAGE_BOOK_LIST_CLIENT;
                }
            }
        }

        return forward;
    }


    public String getParam(HttpServletRequest request) throws DBException, SQLException {
        String forward = Path.PAGE_LIST_BOOK;
        dbManager = DBManager.getInstance();
        if(request.getParameter("search") != null){
            String searchName = request.getParameter("search");
            List<Book> listBook = dbManager.searchBooks(searchName);
            request.setAttribute("listBookClient", listBook);
            forward = Path.PAGE_BOOK_LIST_CLIENT;
            LOG.trace("Forward address --> " + forward);
            LOG.info("listBook ==> " + listBook);
            LOG.debug("SearchCommand search by title finish");
        }
        if(request.getParameter("searchByAuthor") != null){
            String searchByAuthor = request.getParameter("searchByAuthor");
            List<Book> listBook = dbManager.searchBooksByAuthor(searchByAuthor);
            request.setAttribute("listBookClient", listBook);
            forward = Path.PAGE_BOOK_LIST_CLIENT;
            LOG.trace("Forward address --> " + forward);
            LOG.info("listBook searchByAuthor ==> " + listBook);
            LOG.debug("SearchCommand search by author finish");
        }

        return forward;
    }
}
