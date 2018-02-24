<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>GuldeLine</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <%--<link href="style.css" rel="stylesheet" type="text/css" />--%>
  <link rel="stylesheet" type="text/css" href="<c:url value='style.css' />"/>
</head>
<body>
<div id="TopMainPan">
  <div id="topPan">
    <div id="topheaderPan"><img src="images/logo.gif" alt="Gulde Line" width="228" height="54" border="0" title="Gulde Line" />

    </div>
    <div id="topbodyleftPan">
      <ul>
        <li class="home"><a href="/login.jsp">Login page</a></li>
        <li>
          <c:if test="${empty user}">
            <a href="/controller?command=listUnregistered">List Book</a>
          </c:if>
        </li>
        <li><a href="/register.jsp">Register</a></li>
        <li class="contact">Contact us</li>
      </ul>
    </div>

  </div>
</div>
<div id="bodyMainPan">
  <div id="bodyPan">
    <div id="bodyleftPan">
      <div id="linkPan">

      </div>
      <div id="seminnerPan">

      </div>

    </div>
    <div id="bodyrightPan">
      <form action="http://web-mastery.info/" method="post">
        <h2>search</h2>
        <div id="formPan">
          <label>AUTHOR:</label>
          <select>
            <option>lorem ipsum</option>
          </select>
        </div>
        <div id="formPantwo">
          <label>CATAGORY:</label>
          <select>
            <option>lorem ipsum</option>
          </select>
        </div>
        <div id="formPanthree">
          <label>LANGUAGE:</label>
          <select>
            <option>lorem ipsum</option>
          </select>
        </div>
        <input name="" type="submit" value="search" />
      </form>
    </div>
  </div>
</div>
<div id="footermainPan">
  <div id="footerPan">
    <ul>
      <li>main page| </li>
      <li>about us| </li>
      <li>main solutions| </li>
      <li>books selling| </li>
      <li>our support| </li>
      <li>Contact</li>
    </ul>
    <p class="copyright">�All right reserved.</p>
    <ul class="templateworld">
      <li>design by:</li>
      <li>Sokolov A.</li>
    </ul>

  </div>
</div>
</body>
</html>
