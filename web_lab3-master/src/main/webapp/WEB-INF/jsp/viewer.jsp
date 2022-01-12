<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 38095
  Date: 02.12.2021
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../../CSS/style.css">
</head>
<body>
<div class="container">
    <div class="item div_1" id="div1" >
        <br><br><br>
    </div>
    <div class="item" id="div2" >
        <div class = "ytext" id="exch1">Here is text from div 2</div>
        <div class = "item y" >
           Here is text from y
        </div>
    </div>
    <div class="item"  id="div3">
        Here is text from div 3
        <div class="main">
            <ul class="tabs">
                <%
                    if (request.getServletContext().getAttribute("list") != null) {
                        for (String li : (List<String>) request.getServletContext().getAttribute("list")) {
                            out.println(li);
                        }
                    }
                %>
            </ul>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>
    <div class="item" id="div4">
        Here is text from div 4
        <form action="login" method="post">
            <input type="submit" value="Редагувати">
        </form>
    </div>
    <div class="item" id="div5">
        <p>Here is text from div 5</p>
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>

    <div class="item" id="div6">
        <h2>Here is text from div 6</h2>
    </div>
</div>

<script type="text/javascript" src=""></script>
</body>
</html>