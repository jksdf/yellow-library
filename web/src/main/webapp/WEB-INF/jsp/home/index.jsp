<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="body">
    <div class="row">
        <c:forEach items="${users}" var="user">
                    <div class="col-xs-12 col-sm-4 col-md-3 col-lg-2"><!-- bootstrap responsive grid -->
                            <div class="thumbnail">
                                <a href="${pageContext.request.contextPath}/users/${user.id}"><span class="glyphicon glyphicon-user " aria-hidden="true"></span></a><br>
                                <div class="caption">
                                    <h4><c:out value="${user.name}"/></h4>
                                </div>
                            </div>
                        </a>
                    </div>
        </c:forEach>
    </div>


        <c:if test="${isAuthenticated}">
            <p class="text-success">user is authenticated</p>
        </c:if>
        <c:if test="${not isAuthenticated}">
            <p class="text-danger">user is NOT authenticated</p>
        </c:if>
    </jsp:attribute>
</own:mainpage>
