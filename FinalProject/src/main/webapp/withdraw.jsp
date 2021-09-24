<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/css/list.css"%>
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
<%@include file="/js/validationRefilling.js"%></script>
<c:set var="lang" value="${sessionScope.locale.language}"/>
</head>
<body data-lang="${lang}">
<br>
<br>
<br>
<div class="wrapper">
<div class="header">
<fmt:message key="withdraw"/>
<br>
</div>
<c:set var="count" value="${requestScope.count}"/>
<c:set var="card" value="${count.card}"/>

<div class="withdraw">
<c:choose>
<c:when test="${count ne null}">
<table>
<tr>
<th><fmt:message key="count.number"/> </th>
<th><fmt:message key="count.name"/> </th>
<th><fmt:message key="count.amount"/> UAH</th>
<th><fmt:message key="card.number.U"/> </th>
<th><fmt:message key="card.amount"/> UAH</th>
<th><fmt:message key="amount.withdraw"/> UAH </th>
</tr>
<tr>
<td>${count.countNumber}</td>
<td>${count.countName}</td>
<td>${count.amount}</td>
<td>${card.cardNumber}</td>
<td>${card.amount}</td>
<td>
<form name="withdrawCount" action="withdraw" onsubmit="return validation()" method="POST">
<input name="amount" id="inputAmount" type="text">
<br>
<span id="amount"></span>
<input type="hidden" name="countId" id="inputCount" value="${count.id}">
<input type="hidden" name="cardId" id="inputCard" value="${card.id}">
<br>
<button class="but" type="submit" ><fmt:message key="withdraw"/></button>
</form>
</td>
</tr>
</table>
</c:when>
<c:otherwise>
<fmt:message key="user.doesnt.have.counts"/>
</c:otherwise>
</c:choose>
<br>
<a button class="button" href="myAccount"><fmt:message key="my.account"/></a>
</font>
</div>
</div>
</body>
</html>