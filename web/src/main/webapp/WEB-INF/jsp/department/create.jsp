<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage>
<jsp:attribute name="body">

    <div class="col-md-7 center">
    <form:form method="post" action="${pageContext.request.contextPath}/department/create"
               modelAttribute="department" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label"><fmt:message key="department.name"/></form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${shortName_error?'has-error':''}">
            <form:label path="shortName" cssClass="col-sm-2 control-label"><fmt:message
                    key="department.shortName"/></form:label>
            <div class="col-sm-10">
                <form:input path="shortName" cssClass="form-control"/>
                <form:errors path="shortName" cssClass="help-block"/>
            </div>
        </div>

        <button class="btn btn-primary" type="submit"><fmt:message key="department.create"/></button>
    </form:form>
    </div>
</jsp:attribute>
</my:mainpage>
