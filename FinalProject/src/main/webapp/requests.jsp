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
<%@include file="/css/list.css"%>
</style>
</head>
<body>
<div class="wrapper">
<div class="header">
<fmt:message key="requests"/>
<br>
</div>
<c:set var="requests" value="${requestScope.request}"/>

<div class="requests">
<c:choose>
<c:when test="${not empty requests}">
<br>
  <form action="requests" method="get">
        <select name="countItems">
            <option value="3" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
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
<th><fmt:message key="number.request"/></th>
<th><fmt:message key="date"/></th>
<th><fmt:message key="user.email"/></th>
<th><fmt:message key="status"/></th>
<th><fmt:message key="count.number"/></th>
<th><fmt:message key="unblock.count"/> </th>
</tr>
<c:forEach items="${requests}" var="request">
<tr>
<td>${request.requestNumber}</td>
<td>${request.requestDate}</td>
<td>${request.user.email}</td>
<td>${request.statusName.name}</td>
<td>${request.count.countNumber}</td>
<td>
<form name="unblockCount" action="unblockCount"  method="POST">
<input type="hidden" name="countId" id="countID" value="${request.count.id}">
<button class="but" type="submit" ><fmt:message key="unblock.count"/></button>
</form>
</td>
</tr>
</c:forEach>
</table>
<pagination:pagination url="requests" userId="${requests[0].user.id}"/>
<br><br><br>
</c:when>
<c:otherwise>
<br>
<div class="text"><fmt:message key="no.requests"/></div>
<br>
</c:otherwise>
</c:choose>

<a button class="button" href="doneRequests"><fmt:message key="fulfilled.requests"/></a>
<a button class="button" href="account.jsp"><fmt:message key="back"/> </a>
</font>
</div>
</div>
</body>
</html>