<%--
  Created by IntelliJ IDEA.
  User: cokinova
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:mainpage title="Orders">
    <jsp:attribute name="body">

        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Book</th>
                <th>User</th>
                <th>Return Date</th>
                <th>State</th>
                    <%--<th>Details</th> --%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${loans}" var="loan">
                <tr>
                    <td>${loan.id}</td>
                    <td>${loan.bookInstance}</td>
                    <td>${loan.user}</td>
                    <td><fmt:formatDate value="${loan.returnDate}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${loan.loanState}"/></td>
                        <%-- <td>
                            <my:a href="/order/detail/${loan.id}" class="btn btn-primary">View</my:a>
                        </td> --%>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:mainpage>
