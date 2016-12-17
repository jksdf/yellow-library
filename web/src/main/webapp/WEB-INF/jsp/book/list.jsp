<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage>
<jsp:attribute name="body">

    <c:if test="${isEmployee}">
        <a href="${pageContext.request.contextPath}/book/create" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            New book
        </a>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="book.name"/></th>
            <th><fmt:message key="book.author"/></th>
            <th><fmt:message key="book.isbn"/></th>
            <th><fmt:message key="book.department"/></th>
            <th>View</th>
            <c:if test="${isEmployee}">
                <!-- TODO: rename later -->
                <th><fmt:message key="book.instance"/></th>
                <th><fmt:message key="book.editShort"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book">
            <tr>
                <td><a href="${pageContext.request.contextPath}/book/${book.id}">
                    <c:out value="${book.name}"/>
                </a></td>
                <td><c:out value="${book.author}"/></td>
                <td><c:out value="${book.isbn}"/></td>
                <td><c:out value="${book.department.name}"/></td>
                <td class="right">
                    <a href="${pageContext.request.contextPath}/bookinstance?bid=${book.id}">
                        <span class="glyphicon glyphicon-eye-open text-info" aria-hidden="true"></span>
                    </a>
                </td>
                <c:if test="${isEmployee}">
                    <td class="right">
                        <a href="${pageContext.request.contextPath}/bookinstance/new?bid=${book.id}">
                            <span class="glyphicon glyphicon-plus text-success" aria-hidden="true"></span>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/book/${book.id}/edit">
                            <span class="glyphicon glyphicon-pencil text-warning" aria-hidden="true"></span>
                        </a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:mainpage>
