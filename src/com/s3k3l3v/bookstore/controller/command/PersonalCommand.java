package com.s3k3l3v.bookstore.controller.command;

import com.s3k3l3v.bookstore.dao.DBManager;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonalCommand  extends Command{
    private static final long serialVersionUID = 1153978254689586123L;

    private static final Logger LOG = Logger.getLogger(PersonalCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, SQLException {

        String forward = Path.PAGE_PERSONAL;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        List<UserOrderBean> userOrderBeanListBookWasGiven = DBManager.getInstance().getUserOrderBeansForBookWasGiven(userId);
        LOG.trace("Found in DB: userOrderBeanListBookWasGiven --> " + userOrderBeanListBookWasGiven);

//        request.setAttribute("userOrderBeanListBookWasGiven", userOrderBeanListBookWasGiven);

        Date date = new Date();

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

        String date1 =  formatForDateNow.format(date);
        String date2 = "21.01.2018";

        Date dateOne = null;
        Date dateTwo = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        int days = 0;
        int dayBigger10 = 0;
        long difference;
        List<Integer> listDays = new ArrayList<>();
        List<Integer> fineList = new ArrayList<>();

        for (UserOrderBean userOrderBean : userOrderBeanListBookWasGiven) {
            date2 = formatForDateNow.format(userOrderBean.getDatetimes());

            try {
                dateOne = format.parse(date1);
                dateTwo = format.parse(date2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            difference = dateOne.getTime() - dateTwo.getTime();

            days = (int) (difference / (24 * 60 * 60 * 1000));
            if (days > 10) {
                dayBigger10 = (days - 10) * 10;
                fineList.add(dayBigger10);
                System.out.println("fine add ==> " + DBManager.getInstance().updateDateFine(userOrderBean.getOrderBill(), dayBigger10));
            } else {
                fineList.add(0);
            }

            listDays.add(days);
        }
        System.out.println(fineList.toString());

        List<UserOrderBean> userOrderBeanListBookWasGiven1 = DBManager.getInstance().getUserOrderBeansForBookWasGiven(userId);
        LOG.trace("Found in DB: userOrderBeanListBookWasGiven1 --> " + userOrderBeanListBookWasGiven1);

        request.setAttribute("userOrderBeanListBookWasGiven", userOrderBeanListBookWasGiven1);

        List<UserOrderBean> userOrderBeanListForLibrarianBookWasGiven = DBManager.getInstance().getUserOrderBeansForLibrarianBookWasGiven();
        LOG.trace("Found in DB: userOrderBeanListForLibrBookWasGiven --> " + userOrderBeanListForLibrarianBookWasGiven);

//        Collections.sort(userOrderBeanListBookWasGiven, compareById);
        // put user order beans list to request

        request.setAttribute("userOrderBeanListForLibrarianBookWasGiven", userOrderBeanListForLibrarianBookWasGiven);
        return forward;
    }
}
