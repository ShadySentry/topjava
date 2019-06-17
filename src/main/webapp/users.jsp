<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>

<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Users</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>id</th>
            <th>user name</th>
            <th>email</th>
            <th>password</th>
            <th>calories/day</th>
            <th>enabled</th>
            <th>roles</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.password}</td>
                <td>${user.caloriesPerDay}</td>
                <td>${user.enabled}</td>
                <td>${user.roles}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>