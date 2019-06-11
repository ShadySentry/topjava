<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<%--<c:forEach var="meal" items="${meals}">--%>
<%--    <p>${meal.dateTime}</p>--%>
<%--</c:forEach>--%>
<table border="1">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr <c:if test="${meal.excess}">style="color: red" </c:if>>
            <td>
                <f:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" var="parsedDate"/>
                <f:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>${meal.calories}</td>
            <td>${meal.description}</td>
            <td>${meal.excess} bac</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>