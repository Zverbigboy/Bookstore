<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>Personal Information</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>
</head>
<body>
    <header>
        <div class="container">
            <menu class="menu_list">
                <li>
                    <c:if test="${userRole.getName() == 'librarian'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="listOrderBookForLibrarian"/>
                            <input type="submit" value="Go Back" >
                        </form>
                    </c:if>
                </li>
                <li class="menu_list_item">
                    <c:if test="${not empty userRole and userRole.getName() == 'client'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="listBookClient"/>
                            <input type="submit" value="Go back" >
                        </form>
                    </c:if>
                </li>
            </menu>
        </div>
    </header>

    <section>
        <div class="container">
            <c:if test="${not empty userRole and userRole.getName() == 'client'}">
                <table align="center">
                    <caption>
                        <h2>List of books that are on the subscription</h2></caption>
                    <tr>
                        <th>№</th>
                        <th>Title book</th>
                        <th>Fine</th>
                    </tr>
                    <c:set var="k" value="0"/>
                    <c:forEach var="userOrder" items="${userOrderBeanListBookWasGiven}">
                        <tr>
                            <c:set var="k" value="${k+1}"/>
                            <td><c:out value="${k}"/></td>
                            <td><c:out value="${userOrder.statusName}" /></td>
                            <td><c:out value="${userOrder.fine}" /></td>

                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${not empty userRole and userRole.getName() == 'librarian'}">
                    <table class="table" align="center">
                        <caption>
                            <h2>The book was given to readers</h2></caption>
                        <tr>
                            <th>№</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Title book</th>
                            <th>Fine</th>
                            <th>Delete</th>
                        </tr>
                        <c:set var="k" value="0"/>
                        <c:forEach var="userOrder" items="${userOrderBeanListForLibrarianBookWasGiven}">
                            <tr>
                                <c:set var="k" value="${k+1}"/>
                                <td><c:out value="${k}"/></td>
                                <td><c:out value="${userOrder.userFirstName}" /></td>
                                <td><c:out value="${userOrder.userLastName}" /></td>
                                <td><c:out value="${userOrder.statusName}" /></td>
                                <td><c:out value="${userOrder.fine}" /></td>
                                <td>
                                    <form action="/controller" method="post">
                                        <input type="hidden" name="command" value="listOrderBookForLibrarian"/>
                                        <button type="submit" name="deleteOrder" value="${userOrder.orderId}">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
            </c:if>

        </div>
    </section>
    <footer class="footer">
        &copy;FineBook(Sokolov, Java), 2018
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
