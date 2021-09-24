<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="localization" %>
<fmt:setLocale value="${sessionScope.locale.language}" />
<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/css/footer.css"%>
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
<%@include file="/js/validationLogin.js"%></script>
<style>
<%@include file="/css/enterStyle.css"%>
</style>
<c:set var="lang" value="${sessionScope.locale.language}"/>
<c:set var="user" value ="${sessionScope.user}"/>
</head>
<body data-lang="${lang}">
<c:set var="errorMessage" value="${requestScope.errorMessage}"/>
<div class="wrapper">
<div class="header">
<fmt:message key="sign.in"/>
<br>
</div>
<c:choose>
<c:when test="${user eq null}">
<form name="findUser" action="login" onsubmit="return validation()" method="POST">
            <br>
            <input class="user-input" name="email" type="text" placeholder="<fmt:message key='email'/>">
            <span id="email"></span>
            <br>
            <input name="password"  type="password" placeholder="<fmt:message key='password'/>">
            <span id="password"></span>
        <div>
        <button class="button" type="submit" ><fmt:message key='login'/></button>
        </div>
         <a class="button"href="register"><fmt:message key="registration"/> </a>
    </form>
    <c:if test="${errorMessage ne null}">
        <p class="loginMessage"><fmt:message key="${errorMessage}"/></p>
        </c:if>
</c:when>
<c:otherwise>
<br>
<a class="buttonac" href="myAccount"><fmt:message key="my.account"/></a>
</c:otherwise>
</c:choose>
</div>
<localization:localization />
</body>
</html>