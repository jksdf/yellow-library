<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="body">
        <p>Hello world</p>
        <c:forEach items="${users}" var="user">
            <p>${user.name}</p>
        </c:forEach>
        <c:if test="${isAuthenticated}">
            <p>user is authenticated</p>
        </c:if>
        <c:if test="${not isAuthenticated}">
            <p>user is NOT authenticated</p>
        </c:if>
    </jsp:attribute>
</own:mainpage>
