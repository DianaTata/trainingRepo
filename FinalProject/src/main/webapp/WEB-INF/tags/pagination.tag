<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="userId" required="true" type="java.lang.Integer"%>
<%@ attribute name="url" required="true" type="java.lang.String"%>
<div class = "pagination">
<c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
    <c:url var="pageUrl" value="${url}">
        <c:param name="page" value="${i}" />
        <c:if test="${not empty requestScope.paginationDTO.amountOfItems}">
        <c:param name="countItems" value="${requestScope.paginationDTO.amountOfItems}" />
        </c:if>
        <c:if test="${not empty sort}">
        <c:param name="sortBy" value="${sort}" />
        </c:if>
        <c:param name="userId" value="${userId}"/>
    </c:url>
    <a href="${pageUrl}" />${i} </a>
</c:forEach>
</div>