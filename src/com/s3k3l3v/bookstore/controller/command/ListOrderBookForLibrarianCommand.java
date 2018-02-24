package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Book;
import com.s3k3l3v.bookstore.entity.OrderMenu;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.entity.UserOrderBean;
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


public class ListOrderBookForLibrarianCommand extends Command {
    private static final long serialVersionUID = 1863978254689581289L;

    private static final Logger LOG = Logger.getLogger(ListOrderBookForLibrarianCommand.class);

    private static class CompareById implements Comparator<UserOrderBean>, Serializable {
        private static final long serialVersionUID = -1573481565177573283L;

        public int compare(UserOrderBean bean1, UserOrderBean bean2) {
            if (bean1.getOrderId() > bean2.getOrderId()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static Comparator<UserOrderBean> compareById = new ListOrderBookForLibrarianCommand.CompareById();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {

        OrderMenu orderMenu = new OrderMenu();

        String forward = Path.PAGE_BOOK_LIST_CLIENT_ORDER;

        HttpSession session = request.getSession();
        Book book = new Book();

        String sessionUserRole = String.valueOf(session.getAttribute("userRole"));
        if (sessionUserRole.equalsIgnoreCase("librarian")) {
            if (request.getParameter("deleteOrder") != null) {
                int id = Integer.parseInt(request.getParameter("deleteOrder"));
                orderMenu.setId(id);
                orderMenu = DBManager.getInstance().getBookIdFromOrderMenu(id);
                book.setId(orderMenu.getBookId());
                book = DBManager.getInstance().getBook(book.getId());
                boolean updatekNC = DBManager.getInstance().updateBookNC(book);
                if (updatekNC) {
                    DBManager.getInstance().deleteOrder(orderMenu.getId());
                }else {
                    throw new DBException("Cannot delete order with id " + id);
                }
                forward = Path.COMMAND_PRESONAL_PAGE;
                LOG.trace("Forward address --> " + forward);
            }
        }


        List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeansForLibrarian();
        LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

        Collections.sort(userOrderBeanList, compareById);

        // put user order beans list to request
        request.setAttribute("userOrderBeanList", userOrderBeanList);

        List<UserOrderBean> userOrderBeanListBookWasGiven = DBManager.getInstance().getUserOrderBeansForLibrarianBookWasGiven();
        LOG.trace("Found in DB: userOrderBeanListBookWasGiven --> " + userOrderBeanListBookWasGiven);

//        Collections.sort(userOrderBeanListBookWasGiven, compareById);
        // put user order beans list to request

        request.setAttribute("userOrderBeanListBookWasGiven", userOrderBeanListBookWasGiven);

        session.setAttribute("userOrderBeanListBookWasGiven", userOrderBeanListBookWasGiven);
        LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanListBookWasGiven);

        LOG.debug("Commands listOrderBookForLibrarian finished");

        return forward;
    }
}
