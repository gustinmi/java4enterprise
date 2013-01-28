<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page trimDirectiveWhitespaces="true"%>

<c:if test="${empty cachedFragment}">
    <c:set var="cachedFragment" scope="application">
        <em>Some heavy shit generated on <%= new java.util.Date() %></em>
    </c:set>
</c:if>

<span>${applicationScope.cachedFragment}</span>
