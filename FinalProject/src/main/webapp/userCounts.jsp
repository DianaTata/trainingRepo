<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagination" %>

<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/css/myCounts.css"%>
</style>
</head>
<body>
<div class="wrapper">
<div class="header">
<fmt:message key="user.counts"/>
</div>
<c:set var="counts" value="${requestScope.counts}"/>
<c:set var="user" value="${sessionScope.user}"/>
<div class="userCounts">
<c:choose>
<c:when test="${not empty counts}">
<br>
<form action="counts" method="get">
<select name="countItems">
        <option value="3" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
        3 <fmt:message key="element"/>
        </option>
        <option value="5" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
        5 <fmt:message key="elements"/>
        </option>
      </select>
<select name="sortBy">
          <option value="sortByNumberAsc" <c:if test="${'countNumber asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="l.n"/>
          </option>
          <option value="sortByNumberDesc" <c:if test="${'countNumber desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="h.n"/>
          </option>
          <option value="sortByNameAsc" <c:if test="${'countName asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="n.a"/>
          </option>
          <option value="sortByNameDesc" <c:if test="${'countName desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="n.n.a"/>
          </option>
          <option value="sortByAmountAsc" <c:if test="${'amount asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="l.a"/>
          </option>
          <option value="sortByAmountDesc" <c:if test="${'amount desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="h.a"/>
          </option>
          <input type="submit" value="<fmt:message key='sort'/> ">
          <input type="hidden" name="userId" value="${counts[0].user.id}">
      </select>
      </form>
<table>
<tr>
<th><fmt:message key="count.number"/> </th>
<th><fmt:message key="count.name"/> </th>
<th><fmt:message key="amount.U"/> UAH</th>
<th><fmt:message key="status"/></th>
<th><fmt:message key="manage.count"/></th>
<c:if test="${user.role.name == 'client'}">
<th><fmt:message key="refill.count"/></th>
</c:if>
<c:if test="${count.statusName.name == 'blocked'}">
<th><fmt:message key="unblock.count"/> </th>
</c:if>
</tr>
<c:forEach items="${counts}" var="count">
<tr>
<td>${count.countNumber}</td>
<td>${count.countName}</td>
<td>${count.amount}</td>
<td>${count.statusName.name}</td>
<td>
<c:choose>
<c:when test="${count.statusName.name == 'blocked' and user.role.name == 'client'}">
<form name="request" action="sendRequest"  method="POST">
<input type="hidden" name="countId" id="requestUnBlock" value="${count.id}">
<button class="but" type="submit"><fmt:message key="request.unblock"/> </button>
</form>
</c:when>
<c:when test="${count.statusName.name == 'blocked' and user.role.name == 'admin'}">
<form name="unblockCount" action="unblockCount"  method="POST">
<input type="hidden" name="countId" id="countID" value="${count.id}">
<button class="but" type="submit"><fmt:message key="unblock.count"/> </button>
</form></c:when>
<c:otherwise>
<form name="blockCount" action="blockCount"  method="POST">
<input type="hidden" name="countId" id="block" value="${count.id}">
<button class="but" type="submit" ><fmt:message key="block"/></button>
</form>
</c:otherwise>
</c:choose>
</td>
<c:choose>
<c:when test="${count.statusName.name == 'opened' and user.role.name == 'client'}">
<td><a class="but" href="countMy?cardId=${count.card.id}"><fmt:message key="refill"/></a></td>
</c:when>
<c:otherwise>
<c:if test="${user.role.name == 'client'}">
<td></td>
</c:if>
</c:otherwise>
</c:choose>
</tr>
</c:forEach>
</table>
<pagination:pagination url="counts" userId="${counts[0].user.id}"/>
<br>
</c:when>
<c:otherwise>
<c:if test="${user.role.name == 'client'}">
<br>
<div class="text"><fmt:message key="p.add.card"/></div>
<br>
<br>
<a button class="button" href="addCard.jsp"><fmt:message key="add.card"/></a>
</c:if>
</c:otherwise>
</c:choose>
<c:if test="${user.role.name == 'admin'}">
<c:if test="${ empty counts}">
<br>
<div class="text"><fmt:message key="user.doesnt.have.counts"/></div>
<br>
</c:if>
<a button class="button" href="users"> <fmt:message key="users"/></a>
<a button class="button" href="account.jsp"><fmt:message key="back"/> </a>
</c:if>
<c:if test="${user.role.name == 'client'}">
<a button class="button" href="myAccount"><fmt:message key="my.account"/> </a>
</c:if>
</font>
</div>
</div>
</body>
</html>