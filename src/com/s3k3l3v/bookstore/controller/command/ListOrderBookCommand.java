package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.OrderMenu;
import com.s3k3l3v.bookstore.entity.Path;
import com.s3k3l3v.bookstore.entity.User;
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

public class ListOrderBookCommand extends Command {

    private static final long serialVersionUID = 1863978254689586345L;

    private static final Logger LOG = Logger.getLogger(ListOrderBookCommand.class);

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

    private static class CompareByTitle implements Comparator<UserOrderBean>, Serializable {
        private static final long serialVersionUID = -1573481565177573283L;

        public int compare(UserOrderBean bean1, UserOrderBean bean2) {
            if (bean1.getStatusName().charAt(0) > bean2.getStatusName().charAt(0)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static Comparator<UserOrderBean> compareById = new ListOrderBookCommand.CompareById();
    private static Comparator<UserOrderBean> compareByTitle = new ListOrderBookCommand.CompareByTitle();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String forward = Path.PAGE_BOOK_LIST_CLIENT_ORDER;
        OrderMenu orderMenu = new OrderMenu();

        if(request.getParameter("deleteOrder")!=null){
            int id = Integer.parseInt(request.getParameter("deleteOrder"));
            orderMenu.setId(id);
            DBManager.getInstance().deleteOrder(orderMenu.getId());
            forward = Path.PAGE_BOOK_LIST_CLIENT_ORDER;
            LOG.trace("Forward address --> " + forward);
        }

        List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(user.getId());
        LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

        System.out.println(userOrderBeanList.toString());
        Collections.sort(userOrderBeanList, compareByTitle);
        System.out.println(userOrderBeanList.toString());

        // put user order beans list to request
        request.setAttribute("userOrderBeanList", userOrderBeanList);
        LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

        LOG.debug("Commands finished");

        return Path.PAGE_BOOK_LIST_CLIENT_ORDER;
    }


}
