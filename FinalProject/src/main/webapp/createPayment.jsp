<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
<%@include file="/js/validationPayment.js"%></script>
<style>
<%@include file="/css/enterStyle.css"%>
</style>
<c:set var="lang" value="${sessionScope.locale.language}"/>
</head>
<body data-lang="${lang}">
<div class="wrapper">
<div class="header">
<fmt:message key="create.payment"/>
<br>
</div>
<c:choose>
<c:when test="${not empty counts}">
<form name="createPayment" action="createPayment" onsubmit="return validation()" method="POST">
      <select class ="select" name="fromCount">
        <c:forEach items="${counts}" var="count">
          <option value="${count.countNumber}">
             <fmt:message key="from.count"/> ${count.countNumber}
          </option>
        </c:forEach>
      </select>

      <input name="toCount" type="text" placeholder="<fmt:message key='to.count'/>">
      <span id="toCount"></span>
      <input name="amount" type="text" placeholder="<fmt:message key='amount'/> UAH">
      <span id="amount"></span> <br>
      <button class="button" type="submit" ><fmt:message key="create.payment"/></button>

</form>
</c:when>
<c:otherwise>
<p class="loginMessage">
<fmt:message key="users.blocked"/>
</p>
</c:otherwise>
</c:choose>
<a  class="button1" href="account.jsp"><fmt:message key="back"/> </a>
</div>
</body>
</html>