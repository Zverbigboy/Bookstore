<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>Info Users</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>
</head>
<body>
<header>
    <div class="container">
        <menu class="menu_list">
            <li class="menu_list_item">
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <button id="listBook" type="submit" name="listBook">List All Book</button>
                </form>
            </li>
            <li class="menu_list_item">
                <form  action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <button id="addUser" type="submit" name="addUser">Add User</button>
                </form>
            </li>
            <li class="menu_list_item">
                <form  action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <button id="infoUser" type="submit" name="infoUser">Info User</button>
                </form>
            </li>
            <li class="menu_list_item">
                <form  action="/controller" method="post">
                    <input type="hidden" name="command" value="adminMenu"/>
                    <button id="infoBlockUser" type="submit" name="infoBlockUser">Info Block User</button>
                </form>
            </li>
        </menu>
    </div>
</header>

    <section>
        <div class="container">
            <table class="table_blur">
                <caption><h2>List of Books</h2></caption>
                <tr>
                    <th>Id</th>
                    <th>Login</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role id</th>
                    <th>Update User</th>
                    <th>Delete User</th>
                    <th>Block User</th>
                </tr>
                <c:set var="k" value="0"/>
                <c:forEach var="userList" items="${listUser}">
                    <tr>

                        <c:set var="k" value="${k+1}"/>
                        <input type="hidden" name="id" value="<c:out value='${userList.id}' />" />
                        <td>${userList.id}</td>
                        <td>${userList.login}</td>
                        <td>${userList.email}</td>
                        <td>${userList.password}</td>
                        <td>${userList.firstName}</td>
                        <td>${userList.lastName}</td>
                        <td>${userList.roleId}</td>
                        <td>
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="adminMenu"/>
                                <button type="submit" name="updateUser" value="${userList.id}">Update User</button>
                            </form>
                        </td>
                        <td>
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="adminMenu"/>
                                <button type="submit" name="deleteUser" value="${userList.id}">Delete User</button>
                            </form>
                        </td>
                        <td>
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="adminMenu"/>
                                <button type="submit" name="blockUser" value="${userList.id}">Block User</button>
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
