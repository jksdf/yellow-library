<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:mainpage>
    <jsp:attribute name="body">
        <div class="container">
            <div class="alert alert-danger" role="alert">
                <h1>
                    <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                    <fmt:message key="err.looking_on_wrong_place" />
                </h1>
                <h1>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="err.access_denied" /> ;)
                </h1>
            </div>
        </div>
    </jsp:attribute>
</own:mainpage>
