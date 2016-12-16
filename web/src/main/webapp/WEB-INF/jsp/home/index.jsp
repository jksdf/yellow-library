<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="body">
    <c:if test="${not isAuthenticated}">
        <div class="jumbotron">
            <h1 class="display-3"><fmt:message key="yellow_library" /></h1>
            <p class="lead"><fmt:message key="welcome_message" /></p>
            <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/login" role="button">
                <fmt:message key="login.signin"/>
            </a></p>
        </div>
    </c:if>
    </jsp:attribute>
</own:mainpage>
