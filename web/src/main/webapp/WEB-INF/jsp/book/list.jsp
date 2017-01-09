<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage>
    <jsp:attribute name="title">
        <fmt:message key="book.list"/>
    </jsp:attribute>

    <jsp:attribute name="body">

    <c:if test="${isEmployee}">
        <a href="${pageContext.request.contextPath}/book/create" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <fmt:message key="book.new"/>
        </a>
    </c:if>
    <a href="#" class="btn btn-primary" id="search-dialog-button"
       onclick="$('#search-dialog').fadeToggle();$('#search-dialog-button').fadeToggle();">
        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
        <fmt:message key="book.search"/>
    </a><br/>
    <div id="search-dialog" class="col-md-7" style="display: none;">
        <form:form method="get" action="${pageContext.request.contextPath}/book/list"
                   modelAttribute="bookSearch" cssClass="form-horizontal">

            <div class="form-group">
                <form:label path="name" cssClass="col-sm-2 control-label"><fmt:message key="book.name"/></form:label>
                <div class="col-sm-10">
                    <form:input path="name" cssClass="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="isbn" cssClass="col-sm-2 control-label"><fmt:message key="book.isbn"/></form:label>
                <div class="col-sm-10">
                    <form:input path="isbn" cssClass="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="author" cssClass="col-sm-2 control-label"><fmt:message
                        key="book.author"/></form:label>
                <div class="col-sm-10">
                    <form:input path="author" cssClass="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="description" cssClass="col-sm-2 control-label"><fmt:message
                        key="book.description"/></form:label>
                <div class="col-sm-10">
                    <form:input path="description" cssClass="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="departmentIds" cssClass="col-sm-2 control-label"><fmt:message
                        key="book.departments"/></form:label>
                <div class="col-sm-10">
                    <form:select multiple="true" path="departmentIds" cssClass="form-control">
                        <form:option value="-1">&nbsp;</form:option>
                        <c:forEach items="${departments}" var="department">
                            <form:option value="${department.id}"><c:out value="${department.name}"/> (<c:out
                                    value="${department.shortName}"/>)</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>

            <button class="btn btn-primary" type="submit"><fmt:message key="book.search"/></button>
        </form:form>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="book.name"/></th>
            <th><fmt:message key="book.author"/></th>
            <th><fmt:message key="book.isbn"/></th>
            <th><fmt:message key="book.department"/></th>
            <c:if test="${isEmployee}">
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
                <c:if test="${isEmployee}">
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
