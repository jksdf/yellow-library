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
                    :( <fmt:message key="err.not_found" />
                </h1>
            </div>
        </div>
    </jsp:attribute>
</own:mainpage>
