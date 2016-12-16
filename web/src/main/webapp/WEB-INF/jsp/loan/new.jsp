<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage title="New Loan">
<jsp:attribute name="body">
    <form:form method="post" action="" modelAttribute="loanCreate" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="bookInstance" cssClass="col-sm-2 control-label">Book instancies</form:label>
            <div class="col-sm-6">
                <form:select path="bookInstance" cssClass="form-control">
                    <form:option value="" label="" />
                    <form:options items="${bookInstancies}" itemLabel="book.name" itemValue="id" />
                </form:select>
                <form:errors path="bookInstance" cssClass="error"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="user" cssClass="col-sm-2 control-label">Users</form:label>
            <div class="col-sm-6">
                <form:select path="user" cssClass="form-control">
                    <form:option value="" label="" />
                    <form:options items="${users}" itemLabel="name" itemValue="id" />
                </form:select>
                <form:errors path="user" cssClass="error"/>
            </div>
        </div>

        <div class="form-group ${loanState_error?'has-error':''}">
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