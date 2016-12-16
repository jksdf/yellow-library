<%--
  Created by IntelliJ IDEA.
  User: reyvateil
  Date: 15.12.2016
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage title="New Book Instance">
<jsp:attribute name="body">

    <div class="col-md-7 center">
    <form:form method="post" action="${pageContext.request.contextPath}/book/${bookId}/bookinstance/create"
               modelAttribute="bookInstanceCreate" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="bookState" cssClass="col-sm-2 control-label">State</form:label>
            <div class="col-sm-10">
                <form:input path="bookState" cssClass="form-control"/>
                <form:errors path="bookState" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="version" cssClass="col-sm-2 control-label">Version</form:label>
            <div class="col-sm-10">
                <form:input path="version" cssClass="form-control"/>
                <form:errors path="version" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="bookAvailability" cssClass="col-sm-2 control-label">Availability</form:label>
            <div class="col-sm-10">
                <form:select path="bookAvailability" cssClass="form-control">
                    <c:forEach items="${bookAvailabilities}" var="availableOption">
                        <form:option value="${availableOption}">${availableOption}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="bookAvailability" cssClass="error"/>
            </div>
        </div>

        <input type="hidden" name="bookId" value=${bookId}>

        <button class="btn btn-primary" type="submit">Create book instance</button>
    </form:form>
    </div>
</jsp:attribute>
</my:mainpage>