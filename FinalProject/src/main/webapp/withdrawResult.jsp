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
 <fmt:message key="withdraw.result"/>
</div>
<c:set var="message" value="${requestScope.message}"/>

<div class="withdrawResult">
<br>
<p class="text"> <fmt:message key="${message}"/> </p>
<br>

<br>
<div class="group">
<div class="href"><a href="cards?userId=${sessionScope.user.id}"><fmt:message key="my.cards"/></a></div>
<div class="href"><a href="myAccount"><fmt:message key="my.account"/> </a></div></div>
</font>
</div>
</div>
</body>
</html>