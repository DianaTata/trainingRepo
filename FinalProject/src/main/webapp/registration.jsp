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
<%@include file="/js/validationRegister.js"%></script>
<style>
<%@include file="/css/enterStyle.css"%>
</style>
</head>
<body data-lang="${lang}">
<c:set var="lang" value="${sessionScope.locale.language}"/>
<c:set var="random" value="${requestScope.random}"/>
<c:set var="errorMessage" value="${requestScope.errorMessage}"/>
<div class="wrapper">
<div class="header">
<fmt:message key="registration"/>
<br>
</div>
<form name="createUser" action="register" onsubmit="return validation()" method="POST">
            <br>
             <input name="name" type="text" placeholder="<fmt:message key='name.l'/>">
             <span id="name"></span>
            <br>
             <input name="surname" type="text" placeholder="<fmt:message key='surname.l'/>">
             <span id="surname"></span>
            <br>
             <input name="password" type="password" placeholder="<fmt:message key='password'/>">
             <span id="password"></span>
            <br>
             <input name="email" type="text" placeholder="<fmt:message key='email'/>">
             <span id="email"></span>
             <input name="captcha" type="text" placeholder="captcha">
             <br>
             <p class="captcha">${random}</p>
             <input type="hidden" name="random" value="${random}">
            <div>
        <button class="button" type="submit" ><fmt:message key="register"/></button>
        <a class="button" href="login"><fmt:message key="back"/> </a>
         </div>
    </form>
   <c:if test="${errorMessage ne null}">
           <p class="loginMessage"><fmt:message key="${errorMessage}"/></p>
           </c:if>
</div>
<localization:localization />
</body>
</html>