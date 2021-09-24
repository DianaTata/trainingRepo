<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
<%@include file="/js/validationCard.js"%></script>
<style>
<%@include file="/css/enterStyle.css"%>
</style>
<c:set var="lang" value="${sessionScope.locale.language}"/>
</head>
<body data-lang="${lang}">
<div class="wrapper">
<div class="header">
<fmt:message key="card.creation"/>
<br>
</div>
<form name="addCard" action="addCard" onsubmit="return validation()" method="POST">
                <br>
                <input name="cardNumber" type="text" placeholder="<fmt:message key='card.number'/>">
                <span id="cardNumber"></span>
                <br>
                <input name="cvv" type="text" placeholder="<fmt:message key='cvv'/>">
                <span id="cvv"></span>
                <br>
                <input name="expiredDate" type="text" placeholder="<fmt:message key='expired.date'/>">
                <span id="expiredDate"></span>
                <br>
                <input name="amount" type="text" placeholder="<fmt:message key='amount'/> UAH">
                <span id="amount"></span>
                <br>
            <button class="button" type="submit" ><fmt:message key="create.card"/></button>
            <input type="hidden" name="userId" value="${sessionScope.user.id}">
            <a button class="button" href="account.jsp"><fmt:message key="back"/></a>
        </form>
</div>
</body>
</html>