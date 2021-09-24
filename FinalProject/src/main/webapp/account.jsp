<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
<style>
A {text-decoration: none;}
<%@include file="/css/account.css"%>
</style>
<style>
<%@include file="/css/ntext.css"%>
</style>
</head>
<body>
<div class="wrapper">
<div class="header">
<fmt:message key="welcome"/>
</div>
<c:if test="${user.role.name == 'client'}">
<div class="ntext">
<p class="first"><fmt:message key="first.info"/></p>
<p class="first1"><fmt:message key="first1.info"/></p>
<p class="second"><fmt:message key="second.info"/></p>
<p class="second1"><fmt:message key="second1.info"/></p>
<p class="third"><fmt:message key="third.info"/></p>
<p class="third1"><fmt:message key="third1.info"/></p>
</div>
<div class="href1">
<a href="addCardServlet"><fmt:message key="add.card"/></a>
</div>
<div class="href2">
<a href="myAccount"><fmt:message key="myAccount"/></a>
</div>
<div class="href3">
<a href="payment?userId=${user.id}"><fmt:message key="create.payment"/></a>
</div>
</c:if>
<c:if test="${user.role.name == 'admin'}">
<div class="href4"><a href="users"><fmt:message key="users"/></a></div>
<div class="href5"><a href="requests"><fmt:message key="requests"/></a></div>
</c:if>
<div class="logout">
<a href="logout"><fmt:message key="logout"/></a>
</div>
</div>
</div>
<localization:localization />
</body>
</html>