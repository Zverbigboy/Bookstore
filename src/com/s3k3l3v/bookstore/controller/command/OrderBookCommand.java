package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.*;
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
import java.util.LinkedList;
import java.util.List;

public class OrderBookCommand extends Command {
    private static final long serialVersionUID = 1863978254689586123L;

    private static final Logger LOG = Logger.getLogger(OrderBookCommand.class);


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

    private static Comparator<UserOrderBean> compareById = new CompareById();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        LOG.debug("Commands starts");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String forward = Path.PAGE_BOOK_LIST_CLIENT_ORDER;
        Book book = null;

        Order order = new Order();
            String[] idBook = request.getParameterValues("bookId");
            LinkedList<Order> idOrder = new LinkedList<>();
            for (String ids : idBook){
                book = DBManager.getInstance().getBook(Integer.parseInt(ids));
                if (book.getNumberCopies() == 0){
                    throw new DBException("You can not order a book if it is not available");
                }
                System.out.println(DBManager.getInstance().insertOrders(user));
                System.out.println("user idBook "+ user.getId());
                idOrder = DBManager.getInstance().getListOrder(user.getId());
                order = idOrder.getLast();
                System.out.println(DBManager.getInstance().insertOrdersMenu(order.getId(), book.getId()));
                System.out.println(book);

                System.out.println(Integer.parseInt(request.getParameter("numberCopies")));
                DBManager.getInstance().updateBookForOrder(book);
            }


        List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(user.getId());
        LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

        Collections.sort(userOrderBeanList, compareById);

        // put user order beans list to request
        request.setAttribute("userOrderBeanList", userOrderBeanList);
        LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

        LOG.debug("Commands finished");

        return forward;
    }
}
