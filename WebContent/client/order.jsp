<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>
</head>

<body class="divOrder">
<header>
    <div class="container">
        <menu class="menu_list">
            <li class="menu_list_item">
                <c:if test="${not empty userRole and userRole.getName() == 'librarian'}">
                    <%--<c:out value="(${userRole.name})"/>--%>
                    <form action="Logout" method="post">
                        <input type="submit" value="Logout" >
                    </form>
                    <%--<a href="/login.jsp">Go back</a>--%>
                </c:if>
            </li>
            <li>
                <c:if test="${userRole.getName() == 'librarian'}">
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="personalCommand"/>
                        <input type="submit" value="Personal Info" >
                    </form>
                </c:if>
            </li>
            <li class="menu_list_item">
                <c:if test="${not empty userRole and userRole.getName() == 'client'}">
                    <%--<c:out value="(${userRole.name})"/>--%>
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="listBookClient"/>
                        <input type="submit" value="Go back" >
                    </form>
                    <%--<a href="/controller?command=listBookClient">Go back</a>--%>
                </c:if>
            </li>
        </menu>
    </div>
</header>

<section>
    <div class="container">
            <form action="/controller" method="post">
                <div align="left">
                <table class="table" align="left">
                    <caption><h2>Order</h2></caption>
                    <tr>
                        <th>№</th>
                        <th>Order Menu</th>
                        <th>Order Closed</th>
                        <th>Title book</th>
                        <th>Delete book</th>
                        <c:if test="${userRole.getName() == 'librarian'}">
                            <th>Checked book</th>
                        </c:if>
                    </tr>
                    <c:set var="k" value="0"/>
                    <c:forEach var="userOrder" items="${userOrderBeanList}">

                        <tr>
                            <c:set var="k" value="${k+1}"/>
                            <td><c:out value="${k}"/></td>
                            <td><c:out value="${userOrder.orderId}" /></td>
                            <td><c:out value="${userOrder.orderBill}" /></td>
                            <td><c:out value="${userOrder.statusName}" /></td>

                            <td>
                                <form action="/controller" method="post">
                                    <input type="hidden" name="command" value="listOrderBook"/>
                                    <button type="submit" name="deleteOrder" value="${userOrder.orderId}">Delete</button>
                                </form>
                            </td>

                            <c:if test="${not empty userRole and userRole.getName() == 'librarian'}">
                                <td>
                                    <form action="/controller" method="post">
                                        <input type="hidden" name="command" value="giveAnOrder"/>
                                        <button type="submit" name="giveAnOrder" value="${userOrder.orderBill}">Checked</button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
                </div>

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
