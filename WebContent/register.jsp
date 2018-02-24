<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register Page</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/js.js"></script>


</head>
<body>
<header>
    <div class="container">

    </div>
</header>

<section>
    <div class="container">
        <table align="center">
            <tr>
                <td>
                    <h3 align="center">Registration</h3>
                </td>
            </tr>

            <tr>
                <td>
                    <form name="register_form" class="registration" action="controller"
                          method="post" onSubmit="return checkedForm(this)">
                        <input type="hidden" name="command" value="register"/>

                        <strong>Email</strong>:<input type="text" name="email"
                                                      class="field"><br>
                        <strong>Password</strong>:<input type="password" name="password"
                                                         class='field'
                                                         minlength="4"
                                                         maxlength="16" required><br>
                        <strong>Login</strong>:<input type="text" name="login"
                                                      class='field' minlength="3"
                                                      maxlength="16" required>
                        <br>
                        <strong>First Name</strong>:<input type="text" name="firstName"
                                                           class='field' minlength="4"
                                                           maxlength="16"><br>
                        <strong>Last Name</strong>:<input type="text" name="lastName"
                                                          class='field' minlength="4"
                                                          maxlength="16"><br>
                        <strong>Role</strong>:
                        <select name = "roleId"  class='field'>
                            <%--<option value = "0">Админ</option>--%>
                            <option value = "1" selected>Читатель</option>
                            <option value = "2">Библиотекарь</option>
                        </select>
                        <br>
                        <input type="submit" class="validateBtn" value="Register">
                    </form>
                </td>
            </tr>
            <br>
            <tr>
                <td>
                    If you are registered user, please <a href="login.jsp">login</a>.
                </td>
            </tr>
            <tr>
                <td id="footer">&copy;FineBook(Sokolov, Java), 2018</td>
            </tr>

        </table>
    </div>
</section>

<footer class="footer">
    <div class="container">
        &copy;FineBook(Sokolov, Java), 2018
    </div>
</footer>

</body>
</html>