<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="body">
        <div class="container">
        <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="login.login" /></th>
                <th><fmt:message key="user.name" /></th>
                <th><fmt:message key="user.address" /></th>
                <th><fmt:message key="user.total_fines" /></th>
                <c:if test="${isEmployee}">
                    <th><fmt:message key="user.user_type" /></th>
                </c:if>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td><c:out value="${user.login}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.address}" /></td>
                    <td><c:out value="${user.totalFines}" /></td>
                    <c:if test="${isEmployee}">
                        <c:if test="${user.userType == 'EMPLOYEE'}"><td><fmt:message key="user.employee" /></td></c:if>
                        <c:if test="${user.userType == 'CUSTOMER'}"><td><fmt:message key="user.customer" /></td></c:if>
                    </c:if>
                </tr>
            </tbody>
        </table>
        </div>
        </div> <!-- container -->

        <div class="container">
            <form class="form-inline">
                <div class="form-group">
                    <div class="input-group">
                        <input type="number" step="any" name="amount" class="form-control" id="amount" placeholder="<fmt:message key="user.paid_fines" />">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="user.subtract" /></button>
            </form>
        </div>
    </jsp:attribute>
</own:mainpage>
