<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Books Store Application</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>
</head>
<body class="bodyBookList">
    <header>
       <div class="container">
           <menu class="menu_list">
               <li class="menu_list_item">
                   <form action="Logout" method="post">
                       <input type="submit" value="Logout">
                   </form>
               </li>
               <li class="menu_list_item">
                   <form  action="/controller" method="post">
                       <input type="hidden" name="command" value="adminMenu"/>
                       <button id="addBook" type="submit" name="addBook">Add Book</button>
                   </form>
               </li>
               <li class="menu_list_item">
                   <form  action="/controller" method="post">
                       <input type="hidden" name="command" value="adminMenu"/>
                       <button id="infoUser" type="submit" name="infoUser">Info User</button>
                   </form>
               </li>
           </menu>

       </div>
    </header>

    <section>
        <div class="container">
            <h1>Books Management</h1>
            <div class="search">
                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <div class="form-group">
                        <strong>Search by name: </strong>
                        <input type="text" class="form-control" name="search" placeholder="Найти">
                    </div>
                    <button type="submit" class="btn btn-default" name="search">Submit</button>
                </form>
                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <div class="form-group">
                        <strong>Search by author: </strong>
                        <input type="text" name="searchByAuthor" class="form-control" placeholder="Найти"/>
                    </div>
                    <button id="searchByAuthor" type="submit" name="searchByAuthor" class="btn btn-default">Submit</button>
                </form>
                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <div class="form-group">
                        <strong>Search by edition: </strong>
                        <input type="text" name="searchByEditon" class="form-control" placeholder="Найти"/>
                    </div>
                    <button id="searchByEditon" type="submit" name="searchByEditon" class="btn btn-default">Submit</button>
                </form>
                <form class="navbar-form navbar-right" action="/controller" method="post">

                    <input type="hidden" name="command" value="adminMenu"/>
                    <div class="form-group">
                        <strong>Search by date: </strong>
                        <input type="text" name="searchByDate" class="form-control" placeholder="Найти"/>
                    </div>
                    <button id="searchByDate" type="submit" name="searchByDate" class="btn btn-default">Submit</button>
                </form>
                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <button id="listBook" type="submit" name="listBook" class="btn btn-danger">List All Book</button>
                </form>
            </div>

        </div>

        <div align="center">
            <table class="table_blur">
                <caption><h2>List of Books</h2></caption>
                <tr>
                    <th>№</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Edition</th>
                    <th>Date Edition</th>
                    <th>Number Copies</th>
                    <th>Operation</th>
                </tr>
                <c:set var="k" value="0"/>
                <c:forEach var="userOrder" items="${listBook}">
                    <tr>

                        <c:set var="k" value="${k+1}"/>
                        <input type="hidden" name="id" value="<c:out value='${userOrder.id}' />" />
                        <td><c:out value="${k}"/></td>
                        <td><c:out value="${userOrder.title}" /></td>
                        <td><c:out value="${userOrder.author}" /></td>
                        <td><c:out value="${userOrder.editon}" /></td>
                        <td><c:out value="${userOrder.dateEditon}" /></td>
                        <td><c:out value="${userOrder.numberCopies}" /></td>
                        <td>
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="adminMenu"/>
                                <button type="submit" name="updateBook" value="${userOrder.id}">Update Book</button>
                            </form>
                            &nbsp;
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="adminMenu"/>
                                <button type="submit" name="deleteBook" value="${userOrder.id}">Delete Book</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>

        <footer class="footer">
            &copy;FineBook(Sokolov, Java), 2018
        </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
