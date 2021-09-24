<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagination" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
<%@include file="/js/filter.js"%></script>
<style>
<%@include file="/css/myRequests.css"%>
</style>
</head>
<body>
<c:set var="user" value="${sessionScope.user}"/>
<div class="wrapper">
<div class="header">
<fmt:message key="fulfilled.requests"/>
<br>
</div>
<c:set var="doneRequests" value="${requestScope.doneRequests}"/>
<div class="doneRequests">
<c:choose>
<c:when test="${not empty doneRequests}">
<br>
<form action="doneRequests" method="get">
        <select name="countItems">
                    <option value="3" <c:if test="${3 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
                    3 <fmt:message key="element"/>
                    </option>
                    <option value="5" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
                    5 <fmt:message key="elements"/>
                    </option>
                    <option value="50" <c:if test="${50 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
                    50 <fmt:message key="elements"/>
                    </option>
                </select>
                    <input type="submit" value="<fmt:message key='choose'/>">
     </form>

   <input class="form-control" type="text" placeholder="<fmt:message key='search'/>" id="search-text" onkeyup="tableSearch()">
   <br>
   <br>
   <table class="table-striped" id="info-table">
<tr>
<th><fmt:message key="date"/></th>
<th><fmt:message key="number.request"/></th>
<th><fmt:message key="user.email"/></th>
<th><fmt:message key="count.number"/></th>
<th><fmt:message key="status"/></th>
</tr>
<c:forEach items="${doneRequests}" var="doneRequests">
<tr>
<td>${doneRequests.requestDate}</td>
<td>${doneRequests.requestNumber}</td>
<td>${doneRequests.user.email}</td>
<td>${doneRequests.count.countNumber}</td>
<td>${doneRequests.statusName.name}</td>
</tr>
</c:forEach>
</table>
<pagination:pagination url="doneRequests" userId="${doneRequests[0].user.id}"/>
<br>
</c:when>
<c:otherwise>
<br>
<fmt:message key="fulfilled.requests.message"/>
<br>
</c:otherwise>
</c:choose>
<br>
<a button class="button" href="account.jsp"><fmt:message key="back"/> </a>
</font>
</div>
</div>
</body>
</html>