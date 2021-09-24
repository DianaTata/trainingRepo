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
 <fmt:message key="refill.result"/>
</div>
<c:set var="message" value="${requestScope.message}"/>
<div class="refillResult">
<br>
<p class="text"> <fmt:message key="${message}"/> </p>

<div class="group">
<div class="href"><a href="counts?userId=${sessionScope.user.id}"><fmt:message key="counts"/></a></div>
<div class="href"><a href="myAccount"><fmt:message key="my.account"/> </a></div></div>
</font>
</font>
</div>
</div>
</body>
</html>