<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="date_format" var="date_format" />
<fmt:message key="loan.list.title" var="title" />
<my:mainpage title="${title}">
    <jsp:attribute name="body">

        <c:if test="${isEmployee}">
            <a href="${pageContext.request.contextPath}/loan/new" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <fmt:message key="loan.newLoan" />
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/loan/recalculateFines" class="btn btn-info">
            <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
            <fmt:message key="loan.recalculateFines" />
        </a>

        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="loan.bookInstance" /></th>
                <th><fmt:message key="loan.user" /></th>
                <th><fmt:message key="loan.from" /></th>
                <th><fmt:message key="loan.fine" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${loans}" var="loan">
                <tr>
                    <td>${loan.bookInstance.book.name}</td>
                    <td>${loan.user.name}</td>
                    <td><fmt:formatDate value="${loan.dateFrom}" pattern="${date_format}" /></td>
                    <td><c:out value="${loan.fine}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/loan/${loan.id}">
                            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            <fmt:message key="loan.info" />
                        </a>
                    </td>
                    <c:if test="${isEmployee}">
                    <td>
                        <a href="${pageContext.request.contextPath}/loan/${loan.id}/edit">
                            <span class="glyphicon glyphicon-pencil text-warning" aria-hidden="true"></span>
                            <fmt:message key="loan.edit" />
                        </a>
                    </td>
                </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:mainpage>
