package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Book;
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

public class AdminMenuCommand extends Command{

    private static final Logger LOG = Logger.getLogger(AdminMenuCommand.class);

    private DBManager manager = null;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {

        LOG.debug("AdminMenuCommand starts");
        response.setContentType("text/html; charset=UTF-8");
        manager = DBManager.getInstance();


        String forward = Path.PAGE_ERROR_PAGE;

        if(request.getParameter("infoUser") != null){
            forward = Path.PAGE_INFO_USERS;
            List<User> users = manager.getAllUser();
            request.setAttribute("listUser", users);
            LOG.trace("Forward address --> " + forward);
            LOG.info("listUsers ==> " + users);
            LOG.debug("Get list users finish");
        }

        if(request.getParameter("updateUser")!=null){
            int id = Integer.parseInt(request.getParameter("updateUser"));
            System.out.println(id);
            User existingUser = manager.getUser(id);
            request.setAttribute("userForm", existingUser);
            forward = Path.PAGE_ADD_USER;
            LOG.trace("Forward address --> " + forward);
        }
        if(request.getParameter("deleteUser")!=null){
            int id = Integer.parseInt(request.getParameter("deleteUser"));
            User user = new User();
            user.setId(id);
            manager.deleteUser(user);
            forward = Path.PAGE_INFO_USERS;
            List<User> users = manager.getAllUser();
            request.setAttribute("listUser", users);
            LOG.info("listUsers ==> " + users);
            LOG.trace("Forward address --> " + forward);
        }
        if(request.getParameter("addUser") != null){
            forward = Path.PAGE_ADD_USER;
            LOG.trace("Forward address --> " + forward);
        }
        if(request.getParameter("blockUser") != null){
            int id = Integer.parseInt(request.getParameter("blockUser"));
            User userBlock = new User();
            userBlock.setId(id);
            manager.blockUser(userBlock.getId());
            forward = Path.PAGE_INFO_USERS;
            List<User> users = manager.getAllUser();
            request.setAttribute("listUser", users);
            LOG.info("listUsers ==> " + users);
            LOG.trace("Forward address --> " + forward);
        }

        if(request.getParameter("infoBlockUser") != null){
            forward = Path.PAGE_INFO_BLOCK_USER;
            List<User> users = manager.getAllBlockUser();
            request.setAttribute("listUser", users);
            LOG.trace("Forward address --> " + forward);
            LOG.info("listUsers ==> " + users);
            LOG.debug("Get list users finish");
        }
        if(request.getParameter("unBlockUser") != null){
            int id = Integer.parseInt(request.getParameter("unBlockUser"));
            User userUnBlock = new User();
            userUnBlock.setId(id);
            manager.unBlockUser(userUnBlock.getId());
            forward = Path.PAGE_INFO_USERS;
            List<User> users = manager.getAllUser();
            request.setAttribute("listUser", users);
            LOG.info("listUsers ==> " + users);
            LOG.trace("Forward address --> " + forward);
        }


        if(request.getParameter("addBook") != null){
            forward = Path.PAGE_ADMIN_ADD_BOOK;
            LOG.trace("Forward address --> " + forward);
        }

        if(request.getParameter("updateBook")!=null){
            int id = Integer.parseInt(request.getParameter("updateBook"));
            Book existingBook = manager.getBook(id);
            request.setAttribute("book", existingBook);
            forward = Path.PAGE_ADMIN_ADD_BOOK;
            LOG.trace("Forward address --> " + forward);
        }
        if(request.getParameter("deleteBook")!=null){
            int id = Integer.parseInt(request.getParameter("deleteBook"));
            Book book = new Book(id);
            manager.deleteBook(book);
            forward = Path.COMMAND_BOOK_LIST;
            LOG.trace("Forward address --> " + forward);
        }

        if (request.getParameter("listBook") != null) {
            forward = Path.COMMAND_BOOK_LIST;
        }

        if (request.getParameter("search") != null) {
            forward = getParam(request);
        }
        if (request.getParameter("searchByAuthor") != null) {
            forward = getParam(request);
        }
        if (request.getParameter("searchByEditon") != null) {
            forward = getParam(request);
        }
        if (request.getParameter("searchByDate") != null) {
            forward = getParam(request);
        }

        request.setAttribute("flight_info", manager.listAllBooks());
        LOG.debug("AdminMenuCommand finish");


        return forward;
    }

    public String getParam(HttpServletRequest request) throws DBException, SQLException {
        String forward = Path.PAGE_LIST_BOOK;
        manager = DBManager.getInstance();
        if(request.getParameter("search") != null){
            String searchName = request.getParameter("search");
            List<Book> listBook = manager.searchBooks(searchName);
            request.setAttribute("listBook", listBook);
            forward = Path.PAGE_LIST_BOOK;
            LOG.trace("Forward address --> " + forward);
            LOG.info("listBook ==> " + listBook);
            LOG.debug("SearchCommand search by title finish");
        }
        if(request.getParameter("searchByAuthor") != null){
            String searchByAuthor = request.getParameter("searchByAuthor");
            List<Book> listBook = manager.searchBooksByAuthor(searchByAuthor);
            request.setAttribute("listBook", listBook);
            forward = Path.PAGE_LIST_BOOK;
            LOG.trace("Forward address --> " + forward);
            LOG.info("listBook searchByAuthor ==> " + listBook);
            LOG.debug("SearchCommand search by author finish");
        }

        if(request.getParameter("searchByEditon") != null){
            String searchByEditon = request.getParameter("searchByEditon");
            List<Book> listBook = manager.searchBooksByEditon(searchByEditon);
            request.setAttribute("listBook", listBook);
            forward = Path.PAGE_LIST_BOOK;
            LOG.trace("Forward address --> " + forward);
            LOG.info("listBook searchByEditon ==> " + listBook);
            LOG.debug("SearchCommand search by editon finish");
        }

        if(request.getParameter("searchByDate") != null){
            String searchByDate = request.getParameter("searchByDate");
            List<Book> listBook = manager.searchBooksByDate(searchByDate);
            request.setAttribute("listBook", listBook);
            forward = Path.PAGE_LIST_BOOK;
            LOG.trace("Forward address --> " + forward);
            LOG.info("listBook searchByDate ==> " + listBook);
            LOG.debug("SearchCommand search by date finish");
        }

        return forward;
    }
}
