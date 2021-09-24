<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
<%@include file="/js/validationRefilling.js"%></script>
<style>
<%@include file="/css/list.css"%>
</style>
<c:set var="lang" value="${sessionScope.locale.language}"/>
</head>
<body data-lang="${lang}">
<c:set var="count" value="${requestScope.count}"/>
<c:set var="card" value="${count.card}"/>
<br>
<br>
<br>
<div class="wrapper">
<div class="header">
<fmt:message key="refilling"/>
<br>
</div>

<c:choose>
<c:when test="${card ne null}">

<table>
<tr>
<th><fmt:message key="card.number.U"/></th>
<th><fmt:message key="card.amount"/> UAH</th>
<th><fmt:message key="count.number"/></th>
<th><fmt:message key="count.name"/></th>
<th><fmt:message key="count.amount"/> UAH</th>
<th><fmt:message key="amount.to.refill"/> UAH</th>
</tr>
<tr>
<td>${card.cardNumber}</td>
<td>${card.amount}</td>
<td>${count.countNumber}</td>
<td>${count.countName}</td>
<td>${count.amount}</td>
<td>
<form name="refillCount" action="refill" onsubmit="return validation()" method="POST">
<input name="amount" type="text">
<br>
<span id="amount"></span>
<input type="hidden" name="cardId" id="inputCard" value="${card.id}">
<input type="hidden" name="countId" id="inputCount" value="${count.id}">
<br>
<button class="button" type="submit" ><fmt:message key="refill"/></button>
</form>
</td>
</tr>
</table>
<div class="enter1">
<a button class="button" href="myAccount"><fmt:message key="my.account"/></a>
</div>
</c:when>
<c:otherwise>
<fmt:message key="user.doesn't.cards"/>
</c:otherwise>
</c:choose>
</font>
</div>
</body>
</html>