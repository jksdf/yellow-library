<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage title="Loan">
    <jsp:attribute name="body">

        <a href="${pageContext.request.contextPath}/loan/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <fmt:message key="loan.newLoan" />
        </a>

        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="loan.bookInstance" /></th>
                <th><fmt:message key="loan.user" /></th>
                <th><fmt:message key="loan.returnDate" /></th>
                <th><fmt:message key="loan.state" /></th>
                    <%--<th>Details</th> --%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${loans}" var="loan">
                <tr>
                    <td>${loan.bookInstance}</td>
                    <td>${loan.user}</td>
                    <td><fmt:formatDate value="${loan.returnDate}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${loan.loanState}"/></td>
                        <%-- <td>
                            <my:a href="/order/detail/${loan.id}" class="btn btn-primary">View</my:a>
                        </td> --%>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:mainpage>
