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
 <fmt:message key="payment.result"/>
</div>
<c:set var="message" value="${requestScope.message}"/>
<c:set var="user" value="${sessionScope.user}"/>
<div class="paymentResult">
<br>
<p class="text"> <fmt:message key="${message}"/> </p>

<div class="group">
<div class="href"><a href="payments?userId=${user.id}"><fmt:message key="my.payment"/></a></div>
<div class="href"><a href="myAccount"><fmt:message key="my.account"/> </a></div></div>
<br>
</font>
<br>
</div>
</body>
</html>