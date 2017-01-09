<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java"
         trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="date_format" var="date_format"/>
<fmt:message key="loan.list.title" var="title"/>
<my:mainpage title="${title}">
    <jsp:attribute name="body">
        <div class="row">
            <div class="col-md-6">
                <c:if test="${isEmployee}">
                    <a href="${pageContext.request.contextPath}/loan/new" class="btn btn-primary">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <fmt:message key="loan.newLoan"/>
                    </a>
                </c:if>
                <a href="${pageContext.request.contextPath}/loan/recalculateFines"
                   class="btn btn-info">
                    <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
                    <fmt:message key="loan.recalculateFines"/>
                </a>
            </div>
            <c:if test="${isEmployee}">
                <form:form method="get" action="" cssClass="form-horizontal"
                           modelAttribute="filterForm">
                    <div class="col-md-2 col-md-offset-2 text-right">
                        <form:select path="user" cssClass="form-control"
                                     onchange="this.form.submit()">
                            <form:option value="all"> <fmt:message key="loan.list.userfilter.all"/></form:option>
                            <form:options items="${users}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </div>
                    <div class="col-md-2 text-right">
                        <form:select path="filter" cssClass="form-control"
                                     onchange="this.form.submit()">
                            <form:option value="all"> <fmt:message
                                    key="loan.list.allRecords"/></form:option>
                            <form:option value="active"> <fmt:message
                                    key="loan.list.activeRecords"/></form:option>
                            <form:option value="expired"> <fmt:message
                                    key="loan.list.expiredRecords"/></form:option>
                        </form:select>
                    </div>
                </form:form>
            </c:if>
        </div>

        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="loan.bookInstance"/></th>
                <th><fmt:message key="loan.user"/></th>
                <th><fmt:message key="loan.from"/></th>
                <th><fmt:message key="loan.fine"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${loans}" var="loan">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/book/${loan.bookInstance.book.id}/">
                            <span>${loan.bookInstance.book.name} (${loan.bookInstance.version})</span>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user/${loan.user.id}/">
                            <span>${loan.user.name}</span>
                        </a>
                    </td>
                    <td><fmt:formatDate value="${loan.dateFrom}" pattern="${date_format}"/></td>
                    <td><c:out value="${loan.fine}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/loan/${loan.id}">
                            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            <fmt:message key="loan.info"/>
                        </a>
                    </td>
                    <c:if test="${isEmployee}">
                    <td>
                        <a href="${pageContext.request.contextPath}/loan/${loan.id}/edit">
                            <span class="glyphicon glyphicon-pencil text-warning"
                                  aria-hidden="true"></span>
                            <fmt:message key="loan.edit"/>
                        </a>
                    </td>
                </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:mainpage>
