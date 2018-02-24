package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class SearchCommand extends Command {
    private static final long serialVersionUID = 7118987897987112L;

    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

    private DBManager dbManager = null;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        LOG.debug("SearchCommand starts");

        dbManager = DBManager.getInstance();
        String forward = Path.PAGE_LIST_BOOK;

//        if (request.getParameter("search") != null) {
//            forward = getParam(request);
//        }
//        if (request.getParameter("searchByAuthor") != null) {
//            forward = getParam(request);
//        }
//        if (request.getParameter("searchByEditon") != null) {
//            forward = getParam(request);
//        }
//        if (request.getParameter("searchByDate") != null) {
//            forward = getParam(request);
//        }


        return forward;
    }
//
//    public String getParam(HttpServletRequest request) throws DBException, SQLException {
//        String forward = Path.PAGE_LIST_BOOK;
//        dbManager = DBManager.getInstance();
//        if(request.getParameter("search") != null){
//            String searchName = request.getParameter("search");
//            List<Book> listBook = dbManager.searchBooks(searchName);
//            request.setAttribute("listBook", listBook);
//            forward = Path.PAGE_LIST_BOOK;
//            LOG.trace("Forward address --> " + forward);
//            LOG.info("listBook ==> " + listBook);
//            LOG.debug("SearchCommand search by title finish");
//        }
//        if(request.getParameter("searchByAuthor") != null){
//            String searchByAuthor = request.getParameter("searchByAuthor");
//            List<Book> listBook = dbManager.searchBooksByAuthor(searchByAuthor);
//            request.setAttribute("listBook", listBook);
//            forward = Path.PAGE_LIST_BOOK;
//            LOG.trace("Forward address --> " + forward);
//            LOG.info("listBook searchByAuthor ==> " + listBook);
//            LOG.debug("SearchCommand search by author finish");
//        }
//
//        if(request.getParameter("searchByEditon") != null){
//            String searchByEditon = request.getParameter("searchByEditon");
//            List<Book> listBook = dbManager.searchBooksByEditon(searchByEditon);
//            request.setAttribute("listBook", listBook);
//            forward = Path.PAGE_LIST_BOOK;
//            LOG.trace("Forward address --> " + forward);
//            LOG.info("listBook searchByEditon ==> " + listBook);
//            LOG.debug("SearchCommand search by editon finish");
//        }
//
//        if(request.getParameter("searchByDate") != null){
//            String searchByDate = request.getParameter("searchByDate");
//            List<Book> listBook = dbManager.searchBooksByDate(searchByDate);
//            request.setAttribute("listBook", listBook);
//            forward = Path.PAGE_LIST_BOOK;
//            LOG.trace("Forward address --> " + forward);
//            LOG.info("listBook searchByDate ==> " + listBook);
//            LOG.debug("SearchCommand search by date finish");
//        }
//
//        return forward;
//    }
}
