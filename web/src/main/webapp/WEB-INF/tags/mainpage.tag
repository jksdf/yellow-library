<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ attribute name="scripts" fragment="true" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="onw" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">

    <title><c:out value="${title}"/></title>
    <!-- Bootstrap -->
    <!-- Latest compiled and minified CSS -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <!-- Own css file -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/own.css"  crossorigin="anonymous">
    <jsp:invoke fragment="head"/>
</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/"><div class="glyphicon glyphicon-home" ></div>&nbsp;<fmt:message key="yellow_library"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/books"><fmt:message key="nav.books"/> <span class="sr-only">(current)</span></a></li>
                <li><a href="${pageContext.request.contextPath}/loans"><fmt:message key="nav.loans"/> </a></li>
                <li><a href="${pageContext.request.contextPath}/departments"><fmt:message key="nav.departments"/></a></li>
                <li><a href="${pageContext.request.contextPath}/user"><fmt:message key="nav.info"/></a></li>
                <li><a href="${pageContext.request.contextPath}/bookinstance/list"><fmt:message key="nav.bookinstance"/></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${isAuthenticated}">
                    <li><a href="${pageContext.request.contextPath}/logout"><div class="glyphicon glyphicon-log-out"></div>&nbsp;<fmt:message key="nav.logout"/></a></li>
                </c:if>
                <c:if test="${not isAuthenticated}">
                    <li><a href="${pageContext.request.contextPath}/login"><div class="glyphicon glyphicon-log-out"></div>&nbsp;<fmt:message key="login.signin"/></a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<!-- page body -->
<div class="container">
    <jsp:invoke fragment="body"/>
</div>
<!-- footer -->
<footer class="footer">
    <div class="container">
        <p class="text-muted">&copy;&nbsp;<%=java.time.Year.now().toString()%>&nbsp;Yellow team</p>
    </div>
</footer>
</body>
</html>