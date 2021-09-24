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
<fmt:message key="card.creation.result"/>
</div>
<c:set var="message" value="${requestScope.message}"/>
<div class="cardCreationResult">
<br>
<p class="text"> <fmt:message key="${message}"/> </p>
<br>
<div class="group">
<br>
<div class="href"><a href="account.jsp"><fmt:message key="back"/> </a></div></div>
</font>
</div>
</div>
</body>
</html>