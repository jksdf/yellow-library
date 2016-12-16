<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<my:mainpage>
<jsp:attribute name="body">

    <h2><c:out value="${book.name}"/></h2>
    <table class="bookDetail">
        <tr>
            <td><fmt:message key="book.author"/>:</td>
            <td><c:out value="${book.author}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="book.isbn"/>:</td>
            <td><c:out value="${book.isbn}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="book.pages"/>:</td>
            <td><c:out value="${book.pages}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="book.description"/>:</td>
            <td><c:out value="${book.description}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="book.department"/>:</td>
            <td><c:out value="${book.department.name}"/> (<c:out value="${book.department.shortName}"/>)</td>
        </tr>
    </table>

</jsp:attribute>
</my:mainpage>
