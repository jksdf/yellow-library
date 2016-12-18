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
        <table>
            <tbody>
                <tr>
                    <td><fmt:message key="loan.fine" /></td>
                    <td><c:out value="${loan.fine}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="loan.user" /></td>
                    <td>${loan.user.name}</td>
                </tr>
                <tr>
                    <td><fmt:message key="loan.bookInstance" /></td>
                    <td>${loan.bookInstance}</td>
                </tr>
                <tr>
                    <td><fmt:message key="loan.state" /></td>
                    <td><c:out value="${loan.loanState}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="loan.length" /></td>
                    <td><c:out value="${loan.loanLength}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="loan.from" /></td>
                    <td><fmt:formatDate value="${loan.dateFrom}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="loan.returnDate" /></td>
                    <td><fmt:formatDate value="${loan.returnDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
            </tbody>
        </table>

    </jsp:attribute>
</my:mainpage>
