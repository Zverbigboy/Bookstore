package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Order;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.entity.UserOrderBean;
import com.s3k3l3v.bookstore.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GiveAnOrderCommand extends Command{
    private static final long serialVersionUID = 1863978254689512378L;

    private static final Logger LOG = Logger.getLogger(GiveAnOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        LOG.debug("Commands listOrderBookForLibrarian start");
        String idOrder = request.getParameter("giveAnOrder");
        System.out.println("order bill #GiveAnOrderCommand" + idOrder);

        DBManager.getInstance().updateOrderWithLibrarian(Integer.parseInt(idOrder));

        List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeansForLibrarian();
        LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

        Order order = new Order();
        order = DBManager.getInstance().getUserIdFromOrders(Integer.parseInt(idOrder));
        System.out.println(order.getUserId());

        DBManager.getInstance().insertDateFine(Integer.parseInt(idOrder), order.getUserId());

        // put user order beans list to request
        request.setAttribute("userOrderBeanList", userOrderBeanList);
        LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

        List<UserOrderBean> userOrderBeanListBookWasGiven = DBManager.getInstance().getUserOrderBeansForLibrarianBookWasGiven();
        request.setAttribute ("userOrderBeanListBookWasGiven", userOrderBeanListBookWasGiven);
        LOG.debug("Commands listOrderBookForLibrarian finished");
        

        return Path.PAGE_BOOK_LIST_CLIENT_ORDER;
    }
}
