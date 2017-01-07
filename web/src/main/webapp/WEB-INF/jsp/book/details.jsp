<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<my:mainpage>
    <jsp:attribute name="title">
        <fmt:message key="book.description" />
    </jsp:attribute>

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

    <h2><fmt:message key="book.instances"/></h2>
    <c:if test="${isEmployee}">
        <a href="${pageContext.request.contextPath}/bookinstance/new/${book.id}" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <fmt:message key="binstance.new"/>
        </a>
    </c:if>
    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="binstance.version"/></th>
            <th><fmt:message key="binstance.availability"/></th>
            <th><fmt:message key="binstance.state"/></th>
            <c:if test="${isEmployee}">
                <th><fmt:message key="binstance.remove"/></th>
                <th><fmt:message key="binstance.edit"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${instances}" var="instance">
            <tr>
                <td><c:out value="${instance.version}"/></td>
                <td>
                    <c:if test="${instance.bookAvailability == 'AVAILABLE'}">
                        <span class="text-success"><fmt:message key="binstance.available"/></span>
                    </c:if>
                    <c:if test="${instance.bookAvailability == 'BORROWED'}">
                        <span class="text-muted"><fmt:message key="binstance.borrowed"/></span>
                    </c:if>
                    <c:if test="${instance.bookAvailability == 'REMOVED'}">
                        <span class="text-danger"><fmt:message key="binstance.removed"/></span>
                    </c:if>
                </td>
                <td><c:out value="${instance.bookState}"/></td>
                <c:if test="${isEmployee}">
                    <td class="right">
                        <c:if test="${instance.bookAvailability != instanceRemoved}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/bookinstance/${instance.id}/edit/availability/REMOVED">
                                <button type="submit" class="btn btn-xs btn-link">
                                    <span class="glyphicon glyphicon-minus text-warning" aria-hidden="true"></span>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${instance.bookAvailability == instanceRemoved}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/bookinstance/${instance.id}/edit/availability/AVAILABLE">
                                <button type="submit" class="btn btn-xs btn-link">
                                    <span class="glyphicon glyphicon-plus text" aria-hidden="true"></span>
                                </button>
                            </form>
                        </c:if>
                    </td>
                    <td class="right">
                        <c:if test="${instance.bookAvailability == 'AVAILABLE'}">
                            <form method="get"
                                  action="${pageContext.request.contextPath}/bookinstance/${instance.id}/edit/state">
                                <button type="submit" class="btn btn-xs btn-link">
                                    <span class="glyphicon glyphicon-pencil text-warning" aria-hidden="true"></span>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${instance.bookAvailability != 'AVAILABLE'}">
                            <button type="submit" class="btn btn-xs btn-link" disabled>
                                <span class="glyphicon glyphicon-pencil text-muted" aria-hidden="true"></span>
                            </button>
                        </c:if>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:mainpage>
