<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="loan.name" var="title" />
<my:mainpage title="${title}">
    <jsp:attribute name="body">
        <dl class="dl-horizontal">
            <dt><fmt:message key="loan.fine" /></dt>
            <dd><c:out value="${loan.fine}"/></dd>

            <dt><fmt:message key="loan.user" /></dt>
            <dd><c:out value="${loan.user.name}"/></dd>

            <dt><fmt:message key="loan.bookInstance" /></dt>
            <dd>
                <a href="${pageContext.request.contextPath}/book/${loan.bookInstance.book.id}/">
                    <span>${loan.bookInstance.book.name} (${loan.bookInstance.version})</span>
                </a>
            </dd>

            <dt><fmt:message key="loan.state" /></dt>
            <dd><c:out value="${loan.loanState}"/></dd>

            <dt><fmt:message key="loan.length" /></dt>
            <dd><c:out value="${loan.loanLength}"/></dd>

            <dt><fmt:message key="loan.from" /></dt>
            <dd><fmt:formatDate value="${loan.dateFrom}" pattern="yyyy-MM-dd"/></dd>

            <dt><fmt:message key="loan.returnDate" /></dt>
            <dd><fmt:formatDate value="${loan.returnDate}" pattern="yyyy-MM-dd"/></dd>
        </dl>
    </jsp:attribute>
</my:mainpage>