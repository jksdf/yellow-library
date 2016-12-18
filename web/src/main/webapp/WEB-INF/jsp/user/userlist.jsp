<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="body">
        <div class="container">
            <form class="form-inline" method="get" action="${pageContext.request.contextPath}/user/list">
                <div class="form-group">
                    <div class="input-group">
                        <input value="${param.user_name}" type="text" id="user_name" name="user_name" class="form-control" placeholder="<fmt:message key="user.search_by_name" />">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="user.search" /></button>
            </form>
        </div>
        <div class="container">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th><fmt:message key="login.login" /></th>
                        <th><fmt:message key="user.name" /></th>
                        <th><fmt:message key="user.address" /></th>
                        <th><fmt:message key="user.total_fines" /></th>
                        <th><fmt:message key="user.user_type" /></th>
                        <th><fmt:message key="user.detail" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="userVar">

                        <tr>
                            <td><c:out value="${userVar.login}" /></td>
                            <td><c:out value="${userVar.name}" /></td>
                            <td><c:out value="${userVar.address}" /></td>
                            <td><c:out value="${userVar.totalFines}" /></td>
                            <c:if test="${userVar.userType == 'EMPLOYEE'}">
                                <td><fmt:message key="user.employee" /></td>
                            </c:if>
                            <c:if test="${userVar.userType == 'CUSTOMER'}">
                                <td><fmt:message key="user.customer" /></td>
                            </c:if>
                            <td>
                                <a href="${pageContext.request.contextPath}/user/<c:out value="${userVar.id}" />">
                                    <div class="glyphicon glyphicon-info-sign"></div>&nbsp;<fmt:message key="nav.info" />
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div> <!-- container -->
        <c:if test="${empty users}">
            asdf
        </c:if>
    </jsp:attribute>
</own:mainpage>
