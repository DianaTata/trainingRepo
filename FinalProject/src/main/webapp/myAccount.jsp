<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script type="text/javascript">
<%@include file="/js/validationUpdateUser.js"%></script>
<style>
A {
    text-decoration: none;
   }
<%@include file="/css/myAccount.css"%>
</style>
<style>
<%@include file="/css/ntext.css"%>
</style>
</head>
<body>
<script>
       $(function () {
       $('[data-toggle="popover"]').popover()
       })
       $('#myModal').on('shown.bs.modal', function() {
       $('#myInput').focus();
       })
       </script>
<div class="wrapper">
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="name" value="${user.name}"/>
<c:set var="surname" value="${user.surname}"/>
<c:set var="email" value="${user.email}"/>
<div class="header">
<fmt:message key="account"/>
</div>
<div class="data">
<fmt:message key="name"/> : ${name}
<br>
<br>
<fmt:message key="surname"/> : ${surname}
<br>
<br>
Email : ${email} </div>

<c:if test="${user.role.name == 'client'}">
<div class="href1">
<a href="counts?userId=${user.id}">
<fmt:message key="counts"/>
</a>
</div>
<div class="href2">
<a href="cards?userId=${user.id}">
<fmt:message key="my.cards"/>
</a>
</div>
<div class="href3">
<a href="payments?userId=${user.id}">
<fmt:message key="my.payment"/>
</a>
</div>
<div class="href4">
<a href="account.jsp"><fmt:message key="back"/>
</a>
</div>
<br>
<div class="update">

<button type="button" class="myButton" data-bs-toggle="modal" data-bs-target="#modal"><fmt:message key="update"/></button>

<div class="modal fade" id="modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><fmt:message key="update"/></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form name="updateUser" action="updateUser" onsubmit="return validation()" method="POST">
                         <input name="name" type="text" placeholder="<fmt:message key='name.l'/>">
                         <span id="name"></span>
                        <br>
                         <input name="surname" type="text" placeholder="<fmt:message key='surname.l'/>">
                         <span id="surname"></span>
                        <br>
                    <input type="hidden" name="userId" value="${sessionScope.user.id}">
                    <br>
                    <button class="button" type="submit" ><fmt:message key="update"/></button>
                </form>
      </div>

    </div>
  </div>
</div>
</a>
</div>
</c:if>
<div class="logout">
<a href="logout"><fmt:message key="logout"/></a>
</div>
</div>
<p class="first2"><fmt:message key="paymentsinfo"/></p>
<p class="first4"><fmt:message key="my.payment"/></p>
<p class="first3"><fmt:message key="cardsinfo"/></p>
<p class="first5"><fmt:message key="my.cards"/></p>
</div>
<localization:localization />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>