<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/css/infoStyle.css"%>
</style>
</head>
<body>
<div class="wrapper">
<div class="header">
<fmt:message key="info"/>
</div>
<c:set var="message" value="${requestScope.message}"/>
<div class="blockCountResult">
<p class="text"> <fmt:message key="${message}"/> </p>
<c:if test="${user.role.name == 'client'}">
<div class="group">
<div class="href"><a href="counts?userId=${sessionScope.user.id}"><fmt:message key="counts"/></a></div>
<div class="href"><a href="account.jsp"><fmt:message key="back"/> </a></div></div>
</c:if>
<c:if test="${user.role.name == 'admin'}">
<div class="group">
<div class="href"><a href="requests"><fmt:message key="requests"/></a></div>
<div class="href"><a href="users"><fmt:message key="users"/></a></a></div>
<div class="href"><a href="account.jsp"><fmt:message key="my.account"/> </a></div></div>
</c:if>
<div class="group">
<br>

</font>
</div>
</div>
</body>
</html>