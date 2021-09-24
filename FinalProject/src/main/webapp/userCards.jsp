<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagination" %>

<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/css/footer.css"%>
</style>
<style>
<%@include file="/css/myCards.css"%>
</style>
</head>
<body>
<div class="wrapper">
<div class="header">
<fmt:message key="user.cards"/>
<br>
</div>
<c:set var="cards" value="${requestScope.cards}"/>
<div class="userCards">
<c:choose>
<c:when test="${not empty cards}">
<br>
      <form action="cards" method="get">
        <select name="countItems">
            <option value="3" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
            3 <fmt:message key="element"/>
            </option>
            <option value="5" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
            5 <fmt:message key="elements"/>
            </option>
        </select>
        <select name="sortBy">
                  <option value="sortByNumberAsc" <c:if test="${'cardNumber asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
                  <fmt:message key="l.n"/>
                  </option>
                  <option value="sortByNumberDesc" <c:if test="${'cardNumber desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
                  <fmt:message key="h.n"/>
                  </option>
                  <option value="sortByAmountAsc" <c:if test="${'amount asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
                  <fmt:message key="l.a"/>
                  </option>
                  <option value="sortByAmountDesc" <c:if test="${'amount desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
                  <fmt:message key="h.a"/>
                  </option>
            <input type="submit" value="<fmt:message key='sort'/> ">
            <input type="hidden" name="userId" value="${cards[0].userDTO.id}">
            </select>
     </form>
<table>
<tr>
<th><fmt:message key="card.number.U"/></th>
<th><fmt:message key="amount.U"/> UAH</th>
<th><fmt:message key="withdraw.to.card"/></th>
</tr>
<c:forEach items="${cards}" var="card">
<tr>
<td>${card.cardNumber}</td>
<td>${card.amount}</td>
<td><a button class="button" href="cardMy?cardId=${card.id}"><fmt:message key="withdraw"/> </a>
</tr>
</c:forEach>
</table>
<pagination:pagination url="cards" userId="${cards[0].userDTO.id}"/>
<br>

</c:when>
<c:otherwise>
<br>
<div class="text"><fmt:message key="user.doesn't.cards"/></div>
<br>
</c:otherwise>
</c:choose>


<a button class="button" href="myAccount"><fmt:message key="my.account"/></a>
</font>
</div>
</div>
</body>
</html>