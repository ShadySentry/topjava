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
        <th>id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
        <th colspan="2">Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <f:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" var="parsedDate"/>
        <f:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="parsedData"/>
        <form method="post">
            <tr <c:if test="${meal.excess}">style="color: red" </c:if>>
                <td><input size="5" style="border: unset" type="text" name="id" value="${meal.id}"></td>
                <td><input size="14" type="text" name="date" value="${parsedData}"></td>
                <td><input size="7" type="text" value=${meal.description} name="description"></td>
                <td><input size="3" type="number" value=${meal.calories} name="calories"></td>
                <td><input size="3" type="text" value=${meal.excess} name="excess"></td>
                <td>
                    <input type="submit" name="edit" value="Редактировать">
                    <input type="submit" name="delete" value="Удалить">
                </td>
            </tr>
        </form>
    </c:forEach>
    </tbody>
</table>
<%--<form>--%>
<%--    <input type="text" value="sdffffffffffffff" name="123">--%>
<%--    <button formmethod="post">dfg--%>
<%--    </button>--%>
<%--    <button value="123">nopost</button>--%>
<%--    <input type="submit" form>--%>
</form>
</body>
</html>