<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Books catalog</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" >
</head>
<body>

<header>

    <div class="container">
        <menu class="menu_list">
            <li class="menu_list_item">
                <form action="Logout" method="post">
                    <input type="submit" value="Logout" >
                </form>
            </li>
            <li class="menu_list_item">
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="listBookClient"/>
                    <input type="submit" value="My Order" name="myOrder" >
                </form>
            </li>
            <li class="menu_list_item">
                <c:if test="${userRole.getName() == 'client'}">
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="personalCommand"/>
                        <input type="submit" value="Personal Info"  >
                    </form>
                </c:if>
            </li>
        </menu>
    </div>
</header>

    <section>
        <div class="container">
            <h1>Books</h1>
            <div class="search" align="right">

                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="listBookClient"/>
                    <div class="form-group">
                        <strong>Search by name: </strong>
                        <input type="text" class="form-control" name="search" placeholder="Найти">
                    </div>
                    <button type="submit" class="btn btn-default" name="search">Submit</button>
                </form>

                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="listBookClient"/>
                    <div class="form-group">
                        <strong>Search by author: </strong>
                        <input type="text" class="form-control" name="searchByAuthor" placeholder="Найти">
                    </div>
                    <button type="submit" class="btn btn-default" name="searchByAuthor">Submit</button>
                </form>

                <form class="navbar-form navbar-right" action="/controller" method="post">
                    <input type="hidden" name="command" value="listBookClient"/>
                    <button id="listBookClient" type="submit" name="listBookClient" class="btn btn-danger">List All Book</button>
                </form>

            </div>
            <div align="center">
                <table>
                    <caption><h2>Catalog</h2></caption>
                    <tr>
                        <th>№</th>
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="listBookClient"/>
                            <th>
                                <button class="sortButton" name="sortByTitle" type="submit" value="${1}">
                                    Title
                                </button>
                            </th>
                        </form>
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="listBookClient"/>
                            <th>
                                <button class="sortButton" name="sortByAuthor" type="submit" value="${2}">
                                    Author
                                </button>
                            </th>
                        </form>

                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="listBookClient"/>
                            <th>
                                <button class="sortButton" name="sortByEditon" type="submit" value="${3}">
                                    Edition
                                </button>
                            </th>
                        </form>

                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="listBookClient"/>
                            <th>
                                <button class="sortButton" name="sortByDateEditon" type="submit" value="${4}">
                                    Date Edition
                                </button>
                            </th>
                        </form>

                        <th>
                            <button class="sortButton">
                                Number Copies
                            </button>
                        </th>
                        <th>
                            <button class="sortButton">
                                Order
                            </button>
                        </th>
                    </tr>
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="orderBook"/>
                        <c:set var="k" value="0"/>
                        <c:forEach var="userOrder" items="${listBookClient}">

                        <tr>
                            <c:set var="k" value="${k+1}"/>
                            <input type="hidden" name="id" value="<c:out value='${userOrder.id}' />" />
                            <td><c:out value="${k}"/></td>
                            <td><c:out value="${userOrder.title}" /></td>
                            <td><c:out value="${userOrder.author}" /></td>
                            <td><c:out value="${userOrder.editon}" /></td>
                            <td><c:out value="${userOrder.dateEditon}" /></td>
                            <td><c:out value="${userOrder.numberCopies}" /></td>
                            <input type="hidden" name="numberCopies" value="${userOrder.numberCopies}"/>
                            <td><input type="checkbox" name="bookId" value="${userOrder.id}"/></td>
                        </tr>
                        </c:forEach>
                </table>

                <input class="button4" value="Make an order" type="submit"/>
                </form>
            </div>
        </div>
    </section>

<footer class="footer">
    &copy;FineBook(Sokolov, Java), 2018
</footer>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
