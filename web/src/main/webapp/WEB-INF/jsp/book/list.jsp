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
            <th>Name</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Department</th>
            <c:if test="${isEmployee}">
                <!-- TODO: rename later -->
                <th>Order</th>
                <th>Edit</th>
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
                <c:if test="${isEmployee}">
                    <td class="right">
                        <a href="${pageContext.request.contextPath}/book/${book.id}/bookinstance/new">
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
