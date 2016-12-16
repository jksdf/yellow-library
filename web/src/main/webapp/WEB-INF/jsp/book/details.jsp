<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<my:mainpage>
<jsp:attribute name="body">

    <h2><c:out value="${book.getName()}"/></h2>
    <table class="bookDetail">
        <tr>
            <td>Author:</td>
            <td><c:out value="${book.getAuthor()}"/></td>
        </tr>
        <tr>
            <td>ISBN:</td>
            <td><c:out value="${book.getIsbn()}"/></td>
        </tr>
        <tr>
            <td>Pages:</td>
            <td><c:out value="${book.getPages()}"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><c:out value="${book.getDescription()}"/></td>
        </tr>
    </table>

</jsp:attribute>
</my:mainpage>
