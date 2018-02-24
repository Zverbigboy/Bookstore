package com.s3k3l3v.bookstore.entity;


public final class Path {

    // pages
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_REGISTER = "/register.jsp";
    public static final String PAGE_ERROR_PAGE = "/Error.jsp";
    public static final String PAGE_LIST_BOOK = "/pages/BookList.jsp";
    public static final String PAGE_ADMIN_ADD_BOOK = "/pages/BookForm.jsp";
    public static final String PAGE_BOOK_LIST_CLIENT = "/client/catalog.jsp";
    public static final String PAGE_BOOK_LIST_CLIENT_ORDER = "/client/order.jsp";
    public static final String PAGE_BOOK_LIST_UNREGISTERED = "/unregistered.jsp";
    public static final String PAGE_PERSONAL = "/personal.jsp";
    public static final String PAGE_INFO_USERS = "/pages/infoUsers.jsp";
    public static final String PAGE_ADD_USER = "/pages/userForm.jsp";
    public static final String PAGE_INFO_BLOCK_USER = "/pages/infoBlockUsers.jsp";

    // commands
    public static final String COMMAND_BOOK_LIST = "/controller?command=listBook";
    public static final String COMMAND_BOOK_FORM = "/controller?command=addBook";
    public static final String COMMAND_BOOK_LIST_CLIENT = "/controller?command=listBookClient";
    public static final String COMMAND_ORDER_BOOK_LIST = "/controller?command=listOrderBook";
    public static final String COMMAND_ORDER_BOOK_LIST_FOR_LIBRARIAN = "/controller?command=listOrderBookForLibrarian";
    public static final String COMMAND_PRESONAL_PAGE = "/controller?command=personalCommand";



}