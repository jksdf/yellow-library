<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="title">
        <fmt:message key="yellow_library" />
    </jsp:attribute>

    <jsp:attribute name="body">
    <c:if test="${not isAuthenticated}">
        <div class="jumbotron">
            <h1 class="display-3"><fmt:message key="yellow_library" /></h1>
            <p class="lead"><fmt:message key="welcome_message" /></p>
            <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/login" role="button">
                <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>&nbsp;<fmt:message key="login.signin"/>
            </a></p>
        </div>
    </c:if>
    <c:if test="${isAuthenticated}">
        <div class="jumbotron">
            <c:if test="${isEmployee}">
                <h2 class="display-3"><fmt:message key="login.logged_in_employee" /></h2>
            </c:if>
            <c:if test="${isCustomer}">
                <h2 class="display-3"><fmt:message key="login.logged_in" /></h2>
            </c:if>
            <p class="lead"><fmt:message key="login.use_all" /></p>
        </div>
    </c:if>
    </jsp:attribute>
</own:mainpage>
