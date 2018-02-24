package com.s3k3l3v.bookstore.dao;

import com.s3k3l3v.bookstore.entity.*;
import com.s3k3l3v.bookstore.exception.DBException;
import com.s3k3l3v.bookstore.exception.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBManager {

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/bookstore?useUnicode=true&amp;characterEncoding=utf8" +
            "&user=root&password=root";

    private static DBManager instance;

    private DataSource ds;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            ds = (DataSource) envContext.lookup("jdbc/bookstore");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }


    public Connection getConnection() throws SQLException, DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }


    public boolean insertBook(Book book) throws DBException {
        String sql = "INSERT INTO books (title, author, editon, date_editon, number_copies) VALUES (?, ?, ?, ?, ?)";
        boolean rowInserted = false;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getEditon());
            preparedStatement.setDate(4, book.getDateEditon());
            preparedStatement.setInt(5, book.getNumberCopies());

            rowInserted = preparedStatement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_INSERT_BOOK, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }


        return rowInserted;
    }

    public boolean insertUser(User user) throws DBException {
        String sql = "insert into users (login,email, password,first_name, last_name, role_id) values (?,?,?,?,?,?)";
        boolean added = false;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setInt(6, user.getRoleId());
            added = preparedStatement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_INSERT_USER, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return added;
    }

    public boolean insertOrders(User user) throws DBException {
        String sql = "insert into orders (bill, user_id, status_id) values (?,?,?)";
        boolean added = false;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.setInt(3, 0);

            added = preparedStatement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_INSERT_ORDERS, ex);
        }finally {
            close(con);
            close(preparedStatement);
        }

        return added;
    }

    public boolean insertOrdersMenu(int orderId, int bookId) throws DBException {
        String sql = "insert INTO  orders_menu (order_id, book_id) VALUEs (?, ?)";
        boolean add = false;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, bookId);
            add = preparedStatement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_INSERT_ORDERMENU, ex);
        }finally {
            close(con);
            close(preparedStatement);
        }

        return add;
    }

    public OrderMenu getOrderMenu() throws DBException, SQLException {
        OrderMenu orderMenu = null;
        String sql = "SELECT * FROM orders_menu";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);

            rs = preparedStatement.executeQuery();
            if (rs.next()){
                orderMenu.setOrderId(rs.getInt("order_id"));
                orderMenu.setBookId(rs.getInt("book_id"));
            }

        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERMENU, ex);
        }finally {
            close(con, preparedStatement, rs);
        }

        return orderMenu;
    }



    public List<Book> listAllBooks() throws DBException {
        List<Book> listBook = new ArrayList<>();

        String sql = "SELECT * FROM books";

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.createStatement();
            con.setAutoCommit(false);
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String editon = resultSet.getString("editon");
                Date dateEditon = resultSet.getDate("date_editon");
                Integer numberCopies = resultSet.getInt("number_copies");

                Book book = new Book(id, title, author, editon,dateEditon,numberCopies);
                listBook.add(book);
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_BOOK, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return listBook;
    }

    public boolean deleteBook(Book book) throws DBException {
        String sql = "DELETE FROM books where id = ?";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean rowDeleted = false;
        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            statement.setInt(1, book.getId());
            rowDeleted = statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_DELETE_BOOK, ex);
        }finally {
            close(con);
            close(statement);
        }
        return rowDeleted;
    }

    public boolean updateBook(Book book) throws DBException {
        String sql = "UPDATE books SET title = ?, author = ?, editon = ?, date_editon = ?, number_copies = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean rowUpdated = false;
        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getEditon());
            statement.setDate(4, book.getDateEditon());
            statement.setInt(5, book.getNumberCopies());
            statement.setInt(6, book.getId());

            rowUpdated = statement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_BOOK, ex);
        }finally {
            close(con);
            close(statement);
        }

        return rowUpdated;
    }



    public boolean updateBookForOrder(Book book) throws DBException {
        String sql =  "UPDATE books SET number_copies = ? - 1 WHERE id = ?" ;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean rowUpdated = false;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, book.getNumberCopies());
            statement.setInt(2, book.getId());

            rowUpdated = statement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_BOOK_FOR_ORDER, ex);
        }finally {
            close(con);
            close(statement);
        }

        return rowUpdated;
    }

    public Book getBook(int id) throws DBException {
        Book book = null;
        String sql = "SELECT * FROM books WHERE id = ?";
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String editon = resultSet.getString("editon");
                Date dateEditon = resultSet.getDate("date_editon");
                Integer numberCopies = resultSet.getInt("number_copies");


                book = new Book(id, title, author, editon, dateEditon,numberCopies);
            }
            con.commit();
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOK, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return book;
    }

    public User findUserByEmail(String email) throws DBException{
        User user = null;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement("select * from users where email=?");
            con.setAutoCommit(false);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        }catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL, ex);
        }finally {
            close(con, preparedStatement, rs);
        }

        return user;
    }

    public List<Book> searchBooks(String searName) throws DBException {
        List<Book> searchList = new ArrayList<>();
        String str = "'%" + searName + "%'";
        String sql = "SELECT * FROM books WHERE title LIKE " + str;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        Book book = null;

        System.out.println(sql);

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                book = extractBook(rs);
                searchList.add(book);
            }

        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_SEARCH_BOOKS, ex);
        }finally {
            close(con, preparedStatement, rs);
        }

        return searchList;
    }

    public List<Book> searchBooksByAuthor(String search) throws DBException {
        List<Book> searchListByAuthor = new ArrayList<>();
        String concatstr = "'%" + search + "%'";
        String sql = "SELECT * FROM books WHERE books.author LIKE " + concatstr;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Book book = null;

        System.out.println(sql);
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = extractBook(resultSet);
                searchListByAuthor.add(book);
            }

            resultSet.close();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_SEARCH_BOOKS_BY_AUTHOR, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return searchListByAuthor;
    }

    public List<Book> searchBooksByEditon(String searchByEditon) throws DBException {
        List<Book> searchListByEditon = new ArrayList<>();
        String constr = "'%" + searchByEditon + "%'";
        String sql = "SELECT * FROM books WHERE books.editon LIKE " + constr;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Book book = null;

        System.out.println(sql);
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = extractBook(resultSet);
                searchListByEditon.add(book);
            }

        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_SEARCH_BOOKS_BY_EDITON, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return searchListByEditon;
    }

    public List<Book> searchBooksByDate(String searchByDate) throws DBException {
        List<Book> searchListByDate = new ArrayList<>();
        String consr = "'%" + searchByDate + "%'";
        String sql = "SELECT * FROM books WHERE books.date_editon LIKE " + consr;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Book book = null;

        System.out.println(sql);
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = extractBook(resultSet);
                searchListByDate.add(book);
            }

        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_SEARCH_BOOKS_BY_DATE, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return searchListByDate;
    }


    public Book extractBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setEditon(rs.getString("editon"));
        book.setDateEditon(rs.getDate("date_editon"));
        book.setNumberCopies(rs.getInt("number_copies"));
        return book;
    }

    public User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setRoleId(rs.getInt("role_id"));
        return user;
    }

    public Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setBill(rs.getInt("bill"));
        order.setUserId(rs.getInt("user_id"));
        order.setStatusId(rs.getInt("status_id"));
        return order;
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }
    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    public List<UserOrderBean> getUserOrderBeans(int userId) throws DBException {
        List<UserOrderBean> orderUserBeanList = new ArrayList<UserOrderBean>();

        String sql = "SELECT om.id, u.first_name, u.last_name, 1 number_copies, b.title FROM\n" +
                "orders_menu om, books b, orders o,users u, statuses s WHERE om.order_id = o.id AND om.book_id = b.id AND u.id = o.user_id\n" +
                "AND s.id = o.status_id AND  o.status_id <> 3 AND user_id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderUserBeanList.add(extractUserOrderBean(resultSet));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return orderUserBeanList;
    }

    public List<UserOrderBean> getUserOrderBeansForLibrarian() throws DBException {
        List<UserOrderBean> orderUserBeanListForLibrarian = new ArrayList<UserOrderBean>();

        String sql = "SELECT om.id, u.first_name, u.last_name, o.id abonement, b.title FROM\n" +
                "books b, orders o,users u, orders_menu om, statuses s WHERE o.user_id = u.id AND om.book_id = b.id AND om.order_id = o.id\n" +
                "AND s.id = o.status_id AND  o.status_id <> 3";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserOrderBean bean = new UserOrderBean();
                bean.setOrderId(resultSet.getLong("id"));
                bean.setUserFirstName(resultSet.getString("first_name"));
                bean.setUserLastName(resultSet.getString("last_name"));
                bean.setOrderBill(resultSet.getInt("abonement"));
                bean.setStatusName(resultSet.getString("title"));
                orderUserBeanListForLibrarian.add(bean);
//                orderUserBeanListForLibrarian.add(extractUserOrderBean(resultSet));
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS_ORDER_BEANS, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS_ORDER_BEANS, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return orderUserBeanListForLibrarian;
    }

    private UserOrderBean extractUserOrderBean(ResultSet rs)
            throws SQLException {
        UserOrderBean bean = new UserOrderBean();
        bean.setOrderId(rs.getLong("id"));
        bean.setUserFirstName(rs
                .getString("first_name"));
        bean.setUserLastName(rs
                .getString("last_name"));
        bean.setOrderBill(rs.getInt("number_copies"));
        bean.setStatusName(rs.getString("title"));
        return bean;
    }

    public LinkedList<Order> getListOrder(int id) throws DBException {
        Order order = null;
        LinkedList<Order> orderList = new LinkedList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? AND bill <> 0";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();

            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                order = extractOrder(resultSet);
                orderList.add(order);
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return orderList;

    }

    public boolean deleteOrder(int id) throws DBException {
        String sql = "DELETE FROM orders_menu where id = ?";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean rowDeleted = false;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);

            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_DELETE_ORDERSMENU, ex);
        }finally {
            close(con);
            close(statement);
        }
        return rowDeleted;
    }

    public boolean updateOrderWithLibrarian(int idOrder) throws DBException {
        String sql = "UPDATE orders SET status_id = 3 WHERE id = ?";
        boolean updated = false;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);

            preparedStatement.setInt(1, idOrder);

            updated = preparedStatement.executeUpdate() > 0;

            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_ORDER_WITH_ID + idOrder, e);
        }finally {
            close(con);
            close(preparedStatement);
        }
        return updated;
    }

    public List<UserOrderBean> getUserOrderBeansForLibrarianBookWasGiven() throws DBException {
        List<UserOrderBean> orderUserBeanListForLibrarianBookWasGiven = new ArrayList<UserOrderBean>();

        String sql = "SELECT om.id, u.first_name, u.last_name, o.id abonement, b.title, df.datetimes, df.fine FROM\n" +
                "  date_fine df, books b, orders o,users u, orders_menu om, statuses s WHERE\n" +
                "  df.user_id = u.id AND u.id = o.user_id AND\n" +
                "  s.id = o.status_id AND om.order_id = o.id AND b.id = om.book_id AND df.order_id = om.order_id AND status_id <> 0\n" +
                "ORDER BY df.datetimes";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserOrderBean beanBooksUsers = new UserOrderBean();
                beanBooksUsers.setOrderId(resultSet.getLong("id"));
                beanBooksUsers.setUserFirstName(resultSet.getString("first_name"));
                beanBooksUsers.setUserLastName(resultSet.getString("last_name"));
                beanBooksUsers.setOrderBill(resultSet.getInt("abonement"));
                beanBooksUsers.setStatusName(resultSet.getString("title"));
                beanBooksUsers.setDatetimes(resultSet.getDate("datetimes"));
                beanBooksUsers.setFine(resultSet.getInt("fine"));
                orderUserBeanListForLibrarianBookWasGiven.add(beanBooksUsers);
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS_ORDER_BEANS_FOR_LIBRARIAN_BOOK_WAS_GIVEN, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS_ORDER_BEANS_FOR_LIBRARIAN_BOOK_WAS_GIVEN, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return orderUserBeanListForLibrarianBookWasGiven;
    }

    public List<UserOrderBean> getUserOrderBeansForBookWasGiven(int userId) throws DBException {
        List<UserOrderBean> orderUserBeanListForBookWasGiven = new ArrayList<UserOrderBean>();

        String sql = "SELECT om.id, u.first_name, u.last_name, o.id abonement, b.title, df.datetimes, df.fine FROM\n" +
                "                  date_fine df, books b, orders o,users u, orders_menu om, statuses s WHERE\n" +
                "  df.user_id = u.id AND u.id = o.user_id AND\n" +
                "  s.id = o.status_id AND om.order_id = o.id AND b.id = om.book_id AND df.order_id = om.order_id AND status_id <> 0 AND u.id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            preparedStatement.setInt(1, userId);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                UserOrderBean beanBooksUsersID = new UserOrderBean();
                beanBooksUsersID.setOrderId(rs.getLong("id"));
                beanBooksUsersID.setUserFirstName(rs.getString("first_name"));
                beanBooksUsersID.setUserLastName(rs.getString("last_name"));
                beanBooksUsersID.setOrderBill(rs.getInt("abonement"));
                beanBooksUsersID.setStatusName(rs.getString("title"));
                beanBooksUsersID.setDatetimes(rs.getDate("datetimes"));
                beanBooksUsersID.setFine(rs.getInt("fine"));
                orderUserBeanListForBookWasGiven.add(beanBooksUsersID);
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS_ORDER_BEANS_FOR_BOOK_WAS_GIVEN, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS_ORDER_BEANS_FOR_BOOK_WAS_GIVEN, ex);
        }finally {
            close(con, preparedStatement, rs);
        }

        return orderUserBeanListForBookWasGiven;
    }

    public List<User> getAllUser() throws DBException {
        List<User> listUser = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE block <> 1";

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.createStatement();
            con.setAutoCommit(false);
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                listUser.add(extractUser(resultSet));
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_USER, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return listUser;
    }

    public User getUser(int id) throws DBException {
        User user = new User();
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                 user.setEmail(resultSet.getString("email"));
                 user.setPassword(resultSet.getString("password"));
                 user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setRoleId(resultSet.getInt("role_id"));
            }
            con.commit();
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return user;
    }

    public boolean updateUser(User user) throws DBException {
        boolean updated = false;
        String sql = "UPDATE users SET login = ?, email = ?, password = ?, first_name = ?, last_name = ?, role_id = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setInt(6, user.getRoleId());
            statement.setInt(7, user.getId());

            updated = statement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
        }finally {
            close(con);
            close(statement);
        }

        return updated;
    }

    public boolean deleteUser(User user) throws DBException {
        String sql = "DELETE FROM users where id = ?";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean deleted = false;
        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, user.getId());
            deleted = statement.executeUpdate() > 0;

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_DELETE_USER, ex);
        }finally {
            close(con);
            close(statement);
        }
        return deleted;
    }

    public boolean blockUser(int id) throws DBException {
        String sql = "UPDATE users SET block = 1 WHERE id = ?";
        boolean blocked = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, id);

            blocked = preparedStatement.executeUpdate() > 0;

            connection.commit();
        } catch (SQLException ex) {
            rollback(connection);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_USER, ex);
        }finally {
            close(connection);
            close(preparedStatement);
        }

        return blocked;
    }

    public List<User> getAllBlockUser() throws DBException {
        List<User> listBlockUser = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE block <> 0";

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.createStatement();
            con.setAutoCommit(false);
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                listBlockUser.add(extractUser(resultSet));
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_BLOCK_USER, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return listBlockUser;
    }

    public boolean unBlockUser(int id) throws DBException {
        String sql = "UPDATE users SET block = 0 WHERE id = ?";
        boolean unBlocked = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, id);

            unBlocked = preparedStatement.executeUpdate() > 0;

            connection.commit();
        } catch (SQLException ex) {
            rollback(connection);
            throw new DBException(Messages.ERR_CANNOT_UNBLOCK_USER, ex);
        }finally {
            close(connection);
            close(preparedStatement);
        }

        return unBlocked;
    }

    public OrderMenu getBookIdFromOrderMenu(int id) throws DBException {
        String str = "SELECT * FROM orders_menu WHERE id = ?";
        OrderMenu orderMenu = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(str);
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, id);
            rs= preparedStatement.executeQuery();
            while (rs.next()){
                orderMenu = new OrderMenu();
                orderMenu.setId(rs.getInt("id"));
                orderMenu.setOrderId(rs.getInt("order_id"));
                orderMenu.setBookId(rs.getInt("book_id"));

            }

            connection.commit();
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_BOOK_ID_FROM_ORDERMENU, ex);
        }finally {
            close(connection, preparedStatement, rs);
        }

        return orderMenu;
    }

    public boolean updateBookNC(Book book) throws DBException {
        String sql =  "UPDATE books SET number_copies = ? + 1 WHERE id = ?" ;
        Connection con = null;
        PreparedStatement statement = null;

        boolean updateNC = false;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, book.getNumberCopies());
            statement.setInt(2, book.getId());

            updateNC = statement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_NUMBER_COPIES_BOOK, ex);
        }finally {
            close(con);
            close(statement);
        }

        return updateNC;
    }

    public boolean insertDateFine(int idOrder, int idUser) throws DBException {
        boolean insertDate = false;
        String sql = "INSERT INTO date_fine VALUES(DEFAULT, curdate(), ?, ?, DEFAULT)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idUser);

            insertDate = preparedStatement.executeUpdate() > 0;

            connection.commit();
        }catch (SQLException ex) {
            rollback(connection);
            throw new DBException(Messages.ERR_CANNOT_INSERT_DATE_FINE, ex);
        }finally {
            close(connection);
            close(preparedStatement);
        }

        return insertDate;
    }

    public List<DateFine> getDateFineList(int userId) throws DBException {
        List<DateFine> dateFineList = new ArrayList<>();

        String sql = "SELECT df.datetimes, o.id abonement FROM date_fine df,users u, orders o, statuses s, orders_menu om, books b WHERE\n" +
                "  df.user_id = u.id AND u.id = o.user_id AND\n" +
                "  s.id = o.status_id AND om.order_id = o.id AND b.id = om.book_id AND df.order_id = om.order_id AND status_id <> 0 AND u.id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DateFine dateFine = new DateFine();
                dateFine.setDatetimes(resultSet.getDate("datetimes"));
                dateFine.setAbonement(resultSet.getInt("abonement"));
                dateFineList.add(dateFine);
            }

        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_DATE_FINE, ex);
        }finally {
            close(con, preparedStatement, resultSet);
        }

        return dateFineList;
    }

    public boolean updateDateFine(int orderBill, int dayBigger10) throws DBException {
        String sql =  "UPDATE date_fine SET fine = ? WHERE order_id = ?" ;
        Connection con = null;
        PreparedStatement statement = null;

        boolean updateDF = false;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, dayBigger10);
            statement.setInt(2, orderBill);

            updateDF = statement.executeUpdate() > 0;
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_FINE_FROM_DATE_FINE, ex);
        }finally {
            close(con);
            close(statement);
        }

        return updateDF;
    }

    public Order getUserIdFromOrders(int idOrder) throws DBException {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE id = ?";
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            statement.setInt(1, idOrder);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                order = new Order();
                order = extractOrder(resultSet);
            }
            con.commit();
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ID_FROM_ORDERS, ex);
        }finally {
            close(con, statement, resultSet);
        }

        return order;
    }
}
