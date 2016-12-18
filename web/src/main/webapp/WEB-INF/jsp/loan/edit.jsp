<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="loan.edit.title" var="title" />
<my:mainpage title="${title}">
    <jsp:attribute name="head">
        <link rel="stylesheet" crossorigin="anonymous"
              href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css">
    </jsp:attribute>

    <jsp:attribute name="body">
    <form:form method="post" action="" modelAttribute="loan" cssClass="form-horizontal">
        <input type="hidden" name="id" value="${loan.id}" />

        <s:bind path="bookInstance">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="bookInstance" cssClass="col-sm-2 control-label"><fmt:message key="loan.bookInstance" /></form:label>
            <div class="col-sm-10">
                <input type="hidden" name="bookInstance" value="${loan.bookInstance.id}" />
                <form:input path="bookInstance.book.name" disabled="true" cssClass="form-control"/>
                <form:errors path="bookInstance" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <s:bind path="user">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="user" cssClass="col-sm-2 control-label"><fmt:message key="loan.user" /></form:label>
            <div class="col-sm-10">
                <input type="hidden" name="user" value="${loan.user.id}" />
                <form:input path="user.name" disabled="true" cssClass="form-control"/>
                <form:errors path="user" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <s:bind path="loanState">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="loanState" cssClass="col-sm-2 control-label"><fmt:message key="loan.state" /></form:label>
            <div class="col-sm-6">
                <form:input path="loanState" cssClass="form-control"/>
                <form:errors path="loanState" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <s:bind path="loanLength">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="loanLength" cssClass="col-sm-2 control-label"><fmt:message key="loan.length" /></form:label>
            <div class="col-sm-6">
                <form:select path="loanLength" cssClass="form-control">
                    <form:option value="15">15</form:option>
                    <form:option value="30">30</form:option>
                    <form:option value="60">60</form:option>
                </form:select>
                <form:errors path="loanLength" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <s:bind path="dateFrom">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="dateFrom" cssClass="col-sm-2 control-label"><fmt:message key="loan.from" /></form:label>
            <div class="col-sm-6">
                <form:input path="dateFrom" type="datetime" readonly="true" cssClass="form-control" />
                <form:errors path="dateFrom" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <s:bind path="returnDate">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="returnDate" cssClass="col-sm-2 control-label"><fmt:message key="loan.returnDate" /></form:label>
            <div class="col-sm-6">
                <form:input path="returnDate" type="text" cssClass="form-control"/>
                <form:errors path="returnDate" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <s:bind path="fine">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="fine" cssClass="col-sm-2 control-label"><fmt:message key="loan.fine" /></form:label>
            <div class="col-sm-6">
                <form:input path="fine" readonly="true" cssClass="form-control"/>
                <form:errors path="fine" cssClass="help-block"/>
            </div>
        </div>
        </s:bind>

        <div class="form-group">
            <div class="col-sm-6 col-sm-offset-2">
                <button class="btn btn-primary" type="submit"><fmt:message key="loan.save" /></button>
                <a href="${pageContext.request.contextPath}/loan/" class="btn btn-default" role="button"><fmt:message key="loan.cancel" /></a>
            </div>
        </div>
    </form:form>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#returnDate").datepicker({
                format: '<fmt:message key="date_format.bootstrap" />'
            });
        });
    </script>
</jsp:attribute>
</my:mainpage>