<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>User Form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
</head>
<body>
    <header>
        <div class="container">
            <menu class="menu_list">
                <li class="menu_list_item">
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="adminMenu"/>
                        <button id="infoUser" type="submit" name="infoUser">List All User</button>
                    </form>
                </li>
            </menu>
        </div>
    </header>

    <section>
        <div class="container">
                <c:if test="${userForm != null}">
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="updateUser"/>
                    </c:if>
                    <c:if test="${userForm == null}">
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="addUser"/>
                        </c:if>
                        <table border="1" cellpadding="5">
                            <caption>
                                <h2>
                                    <c:if test="${userForm != null}">
                                        Edit user
                                    </c:if>
                                    <c:if test="${userForm == null}">
                                        Add New user
                                    </c:if>
                                </h2>
                            </caption>
                            <%--<form action="/controller" method="post">--%>
                            <%--<input type="hidden" name="command" value="addBook"/>--%>

                            <c:if test="${userForm != null}">
                                <input type="hidden" name="id" value="<c:out value='${userForm.id}' />" />
                            </c:if>
                            <tr>
                                <th>Login: </th>
                                <td>
                                    <input type="text" name="login" size="25"
                                           value="${userForm.login}"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>Email: </th>
                                <td>
                                    <input type="text" name="email" size="25"
                                           value="${userForm.email}"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>Password: </th>
                                <td>
                                    <input type="password" name="password" size="25"
                                           value="${userForm.password}"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>First Name: </th>
                                <td>
                                    <input type="text" name="firstName" size="25"
                                           value="${userForm.firstName}"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>Last Name: </th>
                                <td>
                                    <input type="text" name="lastName" size="25"
                                           value="<c:out value='${userForm.lastName}' />"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>Role: </th>
                                <td>
                                    <select name = "roleId">
                                        <%--<option value = "0">Админ</option>--%>
                                        <option value = "1" selected>Читатель</option>
                                        <option value = "2">Библиотекарь</option>
                                    </select>
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
        <div class="container">
            &copy;FineBook(Sokolov, Java), 2018
        </div>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
