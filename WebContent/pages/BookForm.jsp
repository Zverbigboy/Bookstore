<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Books Store Application</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>

	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
</head>

<body class="divOrder">

	<header>
		<div class="container">

		</div>
	</header>

	<section>
		<div class="container">
			<h1>Books Management</h1>
				<form action="/controller" method="post">
					<input type="hidden" name="command" value="adminMenu"/>
					<button id="addBook" type="submit" name="addBook">Add Book</button>
				</form>
				&nbsp;&nbsp;&nbsp;
				<form action="/controller" method="post">
					<input type="hidden" name="command" value="listBook"/>
					<button id="listBook" type="submit" name="listBook">List All Book</button>
				</form>
		</div>
		<div align="center">
			<c:if test="${book != null}">
			<form action="/controller" method="post">
				<input type="hidden" name="command" value="updateBook"/>
				</c:if>
				<c:if test="${book == null}">
				<form action="/controller" method="post">
					<input type="hidden" name="command" value="addBook"/>
					</c:if>
					<table border="1" cellpadding="5">
						<caption>
							<h2>
								<c:if test="${book != null}">
									Edit Book
								</c:if>
								<c:if test="${book == null}">
									Add New Book
								</c:if>
							</h2>
						</caption>
						<%--<form action="/controller" method="post">--%>
						<%--<input type="hidden" name="command" value="addBook"/>--%>

						<c:if test="${book != null}">
							<input type="hidden" name="id" value="<c:out value='${book.id}' />" />
						</c:if>
						<tr>
							<th>Title: </th>
							<td>
								<input type="text" name="title" size="45"
									   value="<c:out value='${book.title}' />"
								/>
							</td>
						</tr>
						<tr>
							<th>Author: </th>
							<td>
								<input type="text" name="author" size="45"
									   value="<c:out value='${book.author}' />"
								/>
							</td>
						</tr>
						<tr>
							<th>Edition: </th>
							<td>
								<input type="text" name="editon" size="35"
									   value="<c:out value='${book.editon}' />"
								/>
							</td>
						</tr>
						<tr>
							<th>Date Edition: </th>
							<td>
								<input type="date" name="dateEditon" size="5"
									   value="<c:out value='${book.dateEditon}' />"
								/>
							</td>
						</tr>
						<tr>
							<th>Number copies: </th>
							<td>
								<input type="text" name="numberCopies" size="2"
									   value="<c:out value='${book.numberCopies}' />"
								/>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="submit" value="Save" />
							</td>
						</tr>
					</table>
				</form>
		</div>
	</section>


	<footer class="footer">
		&copy;FineBook(Sokolov, Java), 2018
	</footer>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
