<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>
</head>
<body>

    <header>
        <div class="container">
            <form action="Logout" method="post">
                <input type="submit" value="Back to login" >
            </form>
        </div>
    </header>

    <section>
        <div class="container">
            <table>
                <caption><h2>List of Books</h2></caption>
                <tr>
                    <th>№</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Edition</th>
                    <th>Date Edition</th>
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
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>

    <footer>
        <div class="container">
            &copy;FineBook(Sokolov, Java), 2018
        </div>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
