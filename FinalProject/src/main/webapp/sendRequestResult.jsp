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
<fmt:message key="request.result"/>
</div>
<c:set var="result" value="${requestScope.request}"/>

<div class="requestResult">
<br>
<p class="text"> <fmt:message key="${result}"/> </p>
<br>
<div class="group">
<div class="href"><a href="counts?userId=${sessionScope.user.id}"><fmt:message key="counts"/></a></div>
<div class="href"><a href="myAccount"><fmt:message key="my.account"/> </a></div></div>
<br>
</font>
<br>
</div>
</body>
</html>