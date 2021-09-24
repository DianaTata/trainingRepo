<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagination" %>


<!DOCTYPE html>
<html>
<head>
<style>

</style>
<style>
<%@include file="/css/myPayments.css"%>
</style>
</head>
<body>
<div class="wrapper">
<div class="header">
<fmt:message key="user.payments"/>
<br>
</div>
<c:set var="payments" value="${requestScope.payments}"/>

<div class="userPayments">
<c:choose>
<c:when test="${not empty payments}">
<br>
<form action="payments" method="get">
<select name="countItems">
            <option value="3" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
            3 <fmt:message key="element"/>
            </option>
            <option value="5" <c:if test="${5 eq requestScope.paginationDTO.amountOfItems}"> selected </c:if>>
            5 <fmt:message key="elements"/>
            </option>
        </select>
<select name="sortBy">
          <option value="sortByNumberAsc" <c:if test="${'paymentNumber asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="l.n"/>
          </option>
          <option value="sortByNumberDesc" <c:if test="${'paymentNumber desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="h.n"/>
          </option>
          <option value="sortByDateAsc" <c:if test="${'paymentDate asc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="n.d"/>
          </option>
          <option value="sortByDateDesc" <c:if test="${'paymentDate desc' eq requestScope.paginationDTO.sortBy}"> selected </c:if>>
          <fmt:message key="o.d"/>
          </option>
          <input type="submit" value="<fmt:message key='sort'/> ">
          <input type="hidden" name="userId" value="${sessionScope.user.id}">
      </select>
      </form>
<table>
<tr>
<th><fmt:message key="payment.date"/></th>
<th><fmt:message key="payment.number"/></th>
<th><fmt:message key="from.count.u"/> </th>
<th><fmt:message key="to.count.u"/> </th>
<th><fmt:message key="amount.U"/> UAH</th>
<th><fmt:message key="status"/></th>
<th><fmt:message key="approve"/></th>
<th><fmt:message key="decline"/></th>
<th><fmt:message key="send.pdf"/> </th>
</tr>
<c:forEach items="${payments}" var="payment">
<tr>
<td>${payment.paymentDate}</td>
<td>${payment.paymentNumber}</td>
<td>${payment.fromCount.countNumber}</td>
<td>${payment.toCount.countNumber}</td>
<td>${payment.amount}</td>
<td>${payment.statusName.name}</td>
<c:choose>
<c:when test="${payment.statusName.name == 'prepared'}">
<td><form name="approvePayment" action="approvePayment"  method="POST">
    <input type="hidden" name="paymentId" value="${payment.id}">
    <button class="but" type="submit" ><fmt:message key="approve"/> </button>
    </form>
</td>
<td><form name="declinePayment" action="declinePayment"  method="POST">
    <input type="hidden" name="paymentId" value="${payment.id}">
    <button class="but" type="submit" ><fmt:message key="decline"/> </button>
    </form>
</td>
</c:when>
<c:otherwise>
<td><fmt:message key="approved"/></td>
<td><fmt:message key="cant.decline"/></td>
</c:otherwise>
</c:choose>
<td><form name="sendPdf" action="sendPdf"  method="GET">
    <input type="hidden" name="paymentId" value="${payment.id}">
    <button class="but" type="submit" ><fmt:message key="send.report"/> </button>
    </form>
</td>
</tr>
</c:forEach>
</table>
<pagination:pagination url="payments" userId="${payments[0].user.id}"/>
<br>
</c:when>
<c:otherwise>
<br>
<div class="text"><fmt:message key="user.doesnt.have.payments"/></div>
<br>
</c:otherwise>
</c:choose>
<br>
<a button class="button" href="myAccount"><fmt:message key="my.account"/></a>
</font>
</div>
</div>
</body>
</html>