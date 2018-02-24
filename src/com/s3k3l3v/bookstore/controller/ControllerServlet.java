package com.s3k3l3v.bookstore.controller;

import com.s3k3l3v.bookstore.dao.DBManager;
import com.s3k3l3v.bookstore.entity.Book;
import com.s3k3l3v.bookstore.exception.DBException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBManager dbManager ;
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

//		dbManager = new DBManager(jdbcURL, jdbcUsername, jdbcPassword);
		try {
			dbManager = new DBManager();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
				case "/controller/new":
					showNewForm(request, response);
					break;
				case "/controller/insert":
					insertBook(request, response);
					break;
				case "/pages/delete":
					deleteBook(request, response);
					break;
			case "/pages/edit":
				showEditForm(request, response);
				break;
			case "/pages/update":
				updateBook(request, response);
				break;
				case "/pages/Logout":
					logout(request, response);
					break;
				default:
					listBook(request, response);
					break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Logout");
//		response.sendRedirect("localhost:8080/login.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, DBException {
		List<Book> listBook = dbManager.listAllBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/BookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/BookForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException, DBException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = dbManager.getBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/BookForm.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);

	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, DBException {
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String editon = request.getParameter("editon");
		Date dateEditon = Date.valueOf(request.getParameter("dateEditon"));
		Integer numberCopies = Integer.valueOf(request.getParameter("numberCopies"));


		Book newBook = new Book(title, author, editon,dateEditon, numberCopies);
		dbManager.insertBook(newBook);
		response.sendRedirect("/pages/list");
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, DBException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String editon = request.getParameter("editon");
		Date dateEditon = Date.valueOf(request.getParameter("dateEditon"));
		Integer numberCopies = Integer.valueOf(request.getParameter("numberCopies"));

		Book newBook = new Book(id, title, author, editon,dateEditon, numberCopies);
		dbManager.updateBook(newBook);
		response.sendRedirect("/pages/list");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, DBException {
		int id = Integer.parseInt(request.getParameter("id"));

		Book book = new Book(id);
		dbManager.deleteBook(book);
		response.sendRedirect("list");

	}

}
