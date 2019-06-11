<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<%--<h1>Single meal ${singleMeal}</h1>--%>
<h1><%
    String singleMeal = request.getParameter("singleMeal");
    if(singleMeal==null||singleMeal.length()==0){
        %>
    Single meal
    <% }
    else {
        %>
    Single meal <%=singleMeal%>
    <%}
%></h1>
</body>
</html>