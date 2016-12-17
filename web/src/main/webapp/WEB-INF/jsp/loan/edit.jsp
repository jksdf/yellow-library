<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage title="Edit Loan">
<jsp:attribute name="body">
    <form:form method="post" action="" modelAttribute="loan" cssClass="form-horizontal">
        <input type="hidden" name="id" value="${loan.id}" />

        <div class="form-group ${bookInstance_error?'has-error':''}">
            <form:label path="bookInstance" cssClass="col-sm-2 control-label">Book instance</form:label>
            <div class="col-sm-10">
                <input type="hidden" name="bookInstance" value="${loan.bookInstance.id}" />
                <form:input path="bookInstance.book.name" disabled="true" cssClass="form-control"/>
                <form:errors path="bookInstance" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="user" cssClass="col-sm-2 control-label">User</form:label>
            <div class="col-sm-10">
                <input type="hidden" name="user" value="${loan.user.id}" />
                <form:input path="user.name" disabled="true" cssClass="form-control"/>
                <form:errors path="user" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="loanState" cssClass="col-sm-2 control-label">State of the Book</form:label>
            <div class="col-sm-6">
                <form:input path="loanState" cssClass="form-control"/>
                <form:errors path="loanState" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="loanLength" cssClass="col-sm-2 control-label">Loan length</form:label>
            <div class="col-sm-6">
                <form:select path="loanLength" cssClass="form-control">
                    <form:option value="15">15</form:option>
                    <form:option value="30">30</form:option>
                    <form:option value="60">60</form:option>
                </form:select>
                <form:errors path="loanLength" cssClass="error"/>
            </div>
        </div>

        <div class="form-group ${dateFrom_error?'has-error':''}">
            <form:label path="dateFrom" cssClass="col-sm-2 control-label">Date from</form:label>
            <div class="col-sm-6">
                <form:input path="dateFrom" type="datetime"  readonly="true" cssClass="form-control"/>
                <form:errors path="dateFrom" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${returnDate_error?'has-error':''}">
            <form:label path="returnDate" cssClass="col-sm-2 control-label">Return Date</form:label>
            <div class="col-sm-6">
                <form:input path="returnDate" type="date" cssClass="form-control"/>
                <form:errors path="returnDate" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${fine_error?'has-error':''}">
            <form:label path="fine" cssClass="col-sm-2 control-label">Fine</form:label>
            <div class="col-sm-6">
                <form:input path="fine" readonly="true" cssClass="form-control"/>
                <form:errors path="fine" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-6 col-sm-offset-2">
                <button class="btn btn-primary" type="submit">Save</button>
                <a href="${pageContext.request.contextPath}/loan/" class="btn btn-default" role="button">Cancel</a>
            </div>
        </div>
    </form:form>
    </div>
</jsp:attribute>
</my:mainpage>