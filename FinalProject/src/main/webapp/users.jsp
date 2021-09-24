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
<fmt:message key="users"/>
<br>
</div>
<c:set var="users" value="${requestScope.users}"/>

<div class="users">
<c:choose>
<c:when test="${not empty users}">
<br>
<form action="users" method="get">
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
<th><fmt:message key="name"/></th>
<th><fmt:message key="surname"/></th>
<th><fmt:message key="email.u"/></th>
<th><fmt:message key="status"/></th>
<th><fmt:message key="manage.user"/> </th>
<th><fmt:message key="user.counts"/> </th>
</tr>
<c:forEach items="${users}" var="user">
<c:if test="${user.role.name == 'client'}">
<tr>
<td>${user.name}</td>
<td>${user.surname}</td>
<td>${user.email}</td>
<td>${user.statusName.name}</td>
<td>
<c:choose>
<c:when test="${user.statusName.name == 'locked'}">
<form name="unblockUser" action="unblockUser"  method="POST">
<input type="hidden" name="userId" id="unblockUser" value="${user.id}">
<button button class="button" type="submit" ><fmt:message key="unblock"/> </button>
</form>
</c:when>
<c:otherwise>
<form name="blockUser" action="blockUser"  method="POST">
<input type="hidden" name="userId" id="blockUser" value="${user.id}">
<button button class="button" type="submit" ><fmt:message key="block"/></button>
</form>
</c:otherwise>
</c:choose>
</td>
<td>
<a button class="button" href="counts?userId=${user.id}"><fmt:message key="counts.l"/> </a>
</td>
</tr>
</c:if>
</c:forEach>
</table>
<pagination:pagination url="users" userId="${users[0].id}"/>
<br>
</c:when>
<c:otherwise>
<div class="text"><fmt:message key="no.users"/></div>
</c:otherwise>
</c:choose>
<br>
<a button class="button" href="account.jsp"><fmt:message key="back"/></a>
</font>
</div>
</div>
</body>
</html>