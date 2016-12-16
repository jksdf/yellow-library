<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage title="New Loan">
<jsp:attribute name="body">


    <form:form method="post" action="${pageContext.request.contextPath}/loan/create"
               modelAttribute="loanCreate" cssClass="form-horizontal">

        <div class="form-group">
            <form:label path="bookInstance" cssClass="col-sm-2 control-label">Book instancies</form:label>
            <div class="col-sm-6">
                <form:select path="bookInstance" cssClass="form-control">
                    <c:forEach items="${bookInstancies}" var="bookInstance">
                        <form:option value="${bookInstance}">${bookInstance}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="bookInstance" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="user" cssClass="col-sm-2 control-label">Users</form:label>
            <div class="col-sm-6">
                <form:select path="user" cssClass="form-control">
                    <c:forEach items="${users}" var="user">
                        <form:option value="${user}">${user}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="user" cssClass="error"/>
            </div>
        </div>

        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="loanState" cssClass="col-sm-2 control-label">State of the Book</form:label>
            <div class="col-sm-6">
                <form:input path="loanState" cssClass="form-control"/>
                <form:errors path="loanState" cssClass="help-block"/>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Create loan</button>
    </form:form>
    </div>
</jsp:attribute>
</my:mainpage>
