<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js"></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/> </h3>
        <div class="card-body pb-0">
            <form id="filter">
                <div class="row">
                    <div class="offset-1 col-2">
                        <label for="startDate">From date </label>
                        <input type="date" name="startDate" value="${param.startDate}">
                    </div>
                    <div class="col-2">
                        <label for="endDate">To date</label>
                        <input type="date" name="endDate" value="${param.endDate}">
                    </div>
                    <div class="offset-2 col-2">
                        <label for="startTime">From time</label>
                        <input type="time" name="startTime" value="${param.startTime}">
                    </div>
                    <div class="col-2">
                        <label for="endTime">To time </label>
                        <input type="time" name="endTime" value="${param.endTime}"/>
                    </div>
                </div>
            </form>
        </div>
        <div class="card-footer text-right">
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-filter"></span>
                <spring:message code="meal.filter"/>
            </button>
        </div>
    </div>
    <br/>
    <button class="btn btn-primary" onclick="add()">
        <span class="fa fa-plus"></span>
        <spring:message code="meal.add"></spring:message>
    </button>
    <table class="table table-striped" id="dataTable">
        <thead>
        <tr data-mealExcess="${meal.excess}">
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr>
                <td><c:out value="${meal.dateTime}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a class="edit" id="${meal.id}"><span class="fa fa-pencil"></span> </a></td>
                <td><a class="delete" id="${meal.id}"><span class="fa fa-remove"></span> </a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/> </h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime"><spring:message code="meal.dateTime"/> </label>
                        <input type="date" class="form-control" id="dateTime" name="dateTime">
                    </div>

                    <div class="form-group">
                        <label for="description"><spring:message code="meal.description"/> </label>
                        <input type="text" class="form-control" id="description" name="description">
                    </div>

                    <div>
                        <label><spring:message code="meal.calories"/></label>
                        <input type="text" class="form-control" id="calories" name="calories">
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>