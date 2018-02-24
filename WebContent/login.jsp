<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset=UTF-8" />
    <title>
        ${title}
    </title>

    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <%--<link rel="stylesheet" type="text/css" media="screen" href="/css/style.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>--%>
    <link href="css/style_index.css" rel="stylesheet" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/js.js"></script>
</head>
<c:set var="title" value="Login" />
<body>
    <header>
        <div class="container" >

        </div>
    </header>

    <section>
        <div class="container">
            <%--<table align="center">--%>
                <%--<h3 align="center">Login with email and password</h3>--%>
                <%--<tr>--%>
                    <%--<td id="header">--%>
                        <%--<c:if test="${empty user}">--%>
                            <%--<form action="/controller" method="post">--%>
                                <%--<input type="hidden" name="command" value="listUnregistered"/>--%>
                                <%--<button id="listUnregistered" type="submit" name="listUnregistered">List Book</button>--%>
                            <%--</form>--%>
                        <%--</c:if>--%>

                        <%--<c:if test="${not empty user}">--%>

                            <%--<div id="leftHeader">--%>

                                <%--<c:choose>--%>

                                    <%--<c:when test="${userRole.name == 'admin' }">--%>
                                        <%--<form action="/controller" method="post">--%>
                                            <%--<input type="hidden" name="command" value="adminMenu"/>--%>
                                            <%--<button id="addBook" type="submit" name="addBook">Add Book</button>--%>
                                        <%--</form>--%>
                                        <%--<a href="controller?command=adminMenu">List Book</a> &nbsp;--%>
                                    <%--</c:when>--%>

                                    <%--<c:when test="${userRole.name == 'client'}">--%>
                                        <%--<a href="controller?command=listBookClient">List Book</a> &nbsp;--%>
                                    <%--</c:when>--%>
                                <%--</c:choose>--%>
                            <%--</div>--%>

                            <%--<div id="rightHeader" >--%>

                                <%--<c:out value="${user.firstName} ${user.lastName}"/>--%>

                                <%--<c:if test="${not empty userRole}">--%>
                                    <%--<c:out value="(${userRole.name})"/>--%>
                                <%--</c:if>--%>
                                <%--<form action="Logout" method="post">--%>
                                    <%--<input type="submit" value="Logout" >--%>
                                <%--</form>--%>

                            <%--</div>--%>
                        <%--</c:if>--%>

                        <%--<c:if test="${empty user and title ne 'Login'}">--%>
                            <%--<div id="rightHeader">--%>
                                <%--<a href="login.jsp">Login</a>--%>
                            <%--</div>--%>
                        <%--</c:if>--%>

                    <%--</td>--%>
                <%--</tr>--%>

                <%--<tr>--%>
                    <%--<td>--%>
                        <%--<form id="login_form" action="controller" name="login_form"--%>
                              <%--method="post" onSubmit="return checkedForm(this)">--%>
                            <%--<input type="hidden" name="command" value="login"/>--%>
                            <%--<strong>User Email</strong>:<input type="text" name="email"><br>--%>
                            <%--<strong>Password</strong>:<input style="margin-left: 12px" type="password" name="password"><br>--%>
                            <%--<input type="submit" value="Login">--%>
                        <%--</form>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<br>--%>
                <%--<tr>--%>
                    <%--<td>--%>
                        <%--If you are new user, please <a href="register.jsp">register</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>

            <%--</table>--%>
        </div>

        <div class="main">

            <div class="content">
                <p class="title"><span class="text"><img src="/images/lib.png" width="76" height="77" hspace="10" vspace="10" align="middle"></span></p>
                <p class="title">Онлайн библиотека</p>
                <p class="text">Добро пожаловать в библиотеку, где вы сможете найти любую книгу на ваш вкус. Доступны функции поиска, просмотра, сортировки и многие другие.</p>
                <p class="text">Проект находится в разработке, поэтому дизайн и функционал будет постоянно дорабатываться.</p>
                <p class="text">По всем вопросам обращайтесь по адресу <a href="mailto:support@testlibrary.com">support@testlibrary.com</a></p>
                <p>&nbsp;</p>

            </div>

            <div class="login_div">
                <p class="title">Для входа введит свои данные:</p>
                <table align="center">
                    <tr>
                        <td id="header">
                            <c:if test="${empty user}">
                                <form action="/controller" method="post">
                                    <input type="hidden" name="command" value="listUnregistered"/>
                                    <button id="listUnregistered" type="submit" name="listUnregistered">List Book</button>
                                </form>
                            </c:if>

                            <c:if test="${not empty user}">

                                <div id="leftHeader">

                                    <c:choose>

                                        <c:when test="${userRole.name == 'admin' }">
                                            <form action="/controller" method="post">
                                                <input type="hidden" name="command" value="adminMenu"/>
                                                <button id="addBook" type="submit" name="addBook">Add Book</button>
                                            </form>
                                            <a href="controller?command=adminMenu">List Book</a> &nbsp;
                                        </c:when>

                                        <c:when test="${userRole.name == 'client'}">
                                            <a href="controller?command=listBookClient">List Book</a> &nbsp;
                                        </c:when>
                                    </c:choose>
                                </div>

                                <div id="rightHeader" >

                                    <c:out value="${user.firstName} ${user.lastName}"/>

                                    <c:if test="${not empty userRole}">
                                        <c:out value="(${userRole.name})"/>
                                    </c:if>
                                    <form action="Logout" method="post">
                                        <input type="submit" value="Logout" >
                                    </form>

                                </div>
                            </c:if>

                            <c:if test="${empty user and title ne 'Login'}">
                                <div id="rightHeader">
                                    <a href="login.jsp">Login</a>
                                </div>
                            </c:if>

                        </td>
                    </tr>

                    <tr>
                        <td>
                            <form id="login_form" class="login_form" action="controller" name="login_form"
                                  method="post" onSubmit="return checkedForm(this)">
                                <input type="hidden" name="command" value="login"/>
                                <strong>User Email</strong>:<input type="text" name="email"><br>
                                <strong>Password</strong>:<input style="margin-left: 12px" type="password" name="password"><br>
                                <input type="submit" value="Login">
                            </form>
                        </td>
                    </tr>
                    <br>
                    <tr>
                        <td>
                            If you are new user, please <a href="register.jsp">register</a>
                        </td>
                    </tr>

                </table>
            </div>

        </div>
    </section>

    <footer class="footer">
        <%--<div class="container">--%>
            &copy;FineBook(Sokolov, Java), 2018
        <%--</div>--%>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>