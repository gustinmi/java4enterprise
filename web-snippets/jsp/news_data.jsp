<%@ page isELIgnored="false" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" info="News data page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="varNewsId" value="${param.paramNewsId}" />
<sql:query var="rsNews" dataSource="jdbc/gostinecDB">
	SELECT id, title, short_description, long_description, created_at, updated_at FROM gos_news WHERE id = ${varNewsId}
</sql:query>	
<div class="content-title" style="background:url(<%=request.getContextPath()%>/images/categories-news.png) no-repeat left top;">Podrobnosti novice</div>
<div class="content">
    <c:forEach var="newsItem" items="${rsNews.rows}">
    	<c:set var="varName" value="${newsItem.title}" />
    	<c:set var="varShortDesc" value="${newsItem.short_description}" />
    	<c:set var="varLongDesc" value="${newsItem.long_description}" />
    	<c:set var="varCreated" value="${newsItem.created_at}" />
		<div class="category-title-container"><b>${varName}</b></div>
		<div class="text-container-full-width">
            ${varCreated} <br />
        </div>
        <div class="text-container">
		<b>${varShortDesc}</b>
	    </div>
        <div class="text-container wordwrap" >
        <pre>${varLongDesc}</pre>
        </div>
    </c:forEach>
</div>

  