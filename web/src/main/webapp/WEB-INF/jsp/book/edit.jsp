<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage>
<jsp:attribute name="body">

    <h2><fmt:message key="book.edit"/></h2>
    <form:form method="post" action="${pageContext.request.contextPath}/book/edit"
               modelAttribute="book" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label"><fmt:message key="book.name"/></form:label>
            <div class="col-sm-10">
                <form:input path="name" disabled="true" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${isbn_error?'has-error':''}">
            <form:label path="isbn" cssClass="col-sm-2 control-label"><fmt:message key="book.isbn"/></form:label>
            <div class="col-sm-10">
                <form:input path="isbn" disabled="true" cssClass="form-control" id="isbnInput"/>
                <form:errors path="isbn" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${author_error?'has-error':''}">
            <form:label path="author" cssClass="col-sm-2 control-label"><fmt:message key="book.author"/></form:label>
            <div class="col-sm-10">
                <form:input path="author" disabled="true" cssClass="form-control"/>
                <form:errors path="author" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label"><fmt:message
                    key="book.description"/></form:label>
            <div class="col-sm-10">
                <form:input path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${pages_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label"><fmt:message
                    key="book.pages"/></form:label>
            <div class="col-sm-10">
                <form:input path="pages" cssClass="form-control"/>
                <form:errors path="pages" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="departmentId" cssClass="col-sm-2 control-label"><fmt:message
                    key="book.department"/></form:label>
            <div class="col-sm-10">
                <form:select path="departmentId" cssClass="form-control">
                    <c:forEach items="${departments}" var="department">
                        <form:option value="${department.id}"><c:out value="${department.name}"/> (<c:out
                                value="${department.shortName}"/>)</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="departmentId" cssClass="error"/>
            </div>
        </div>

        <input type="hidden" name="id" value="${book.id}" disabled>

        <button class="btn btn-primary" type="submit"><fmt:message key="book.edit"/></button>
    </form:form>
</jsp:attribute>
</my:mainpage>
