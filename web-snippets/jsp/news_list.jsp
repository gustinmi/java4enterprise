<%@ page isELIgnored="false" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" info="list of news page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%-- Mode is: (on index page, on news list page --%>
<c:set var="varMode" value="${param.mode}" />
<c:set var="varLimit" value="${param.limit}" />

<%-- Set the limit of news to display --%>
<c:set var="varLimitFilter">
    <c:choose>
        <c:when test="${!empty varLimit}">
            <c:out value="LIMIT 0, ${varLimit}" />
        </c:when>
    </c:choose>
</c:set>

<%
    //pager settings
    String style = "simple"; 
    String position = "both"; 
    String index = "center"; 
    int maxPageItems =  10;
    int maxIndexPages = 10;
%>


<c:if test="${varMode ne 'home'}">
   <div class="content-title" style="background:url(<%=request.getContextPath()%>/images/categories-news.png) no-repeat left top;">Novice</div>
</c:if>
<div class="content">
    <c:if test="${varMode eq 'home'}">
        <c:url var="varToAllNewsLink" value="/news.jsp">
        </c:url>
        <div class="category-title-container">
            <a href="<c:out value="${varToAllNewsLink}" />" class="category-title-link">Novice</a>
        </div>
    </c:if>

	<sql:query var="rsNews" dataSource="jdbc/gostinecDB">
		SELECT id, title, LEFT(short_description, 200) shortDesc, LEFT(long_description, 50) longDesc, active, created_at, updated_at
		FROM gos_news
		WHERE active = 1
		ORDER BY updated_at DESC
		${varLimitFilter}
	</sql:query>
	<c:set var="varNavigationSize">
        <c:out value='${rsNews.rowCount}'/>
    </c:set>	

    <pg:pager id="lista"
		index="<%= index %>"
		maxPageItems="<%= maxPageItems %>"
		maxIndexPages="<%= maxIndexPages %>"
		export="offset,currentPageNumber=pageNumber"
		scope="request">
                
        <pg:param name="maxPageItems"/>
        <pg:param name="maxIndexPages"/>
        <pg:param name="sekcija" value="novice" />
        <input type="hidden" name="pager.offset" value="<%= offset %>" />
	    <div class="paginationTop"></div>
	    <c:forEach var="newsItem" items="${rsNews.rows}">
			<c:set var="varNewsId" value="${newsItem.id}" />
	    	<c:url var="varToNewsLink" value="/news.jsp">
				<c:param name="id" value="${varNewsId}"/>
			</c:url>
			
			<pg:item>
				<div class="text-container-full-width">
			    	<a href="<c:out value="${varToNewsLink}" />" class="suppliers-categories-link"><c:out value="${newsItem.title}" /></a><br/>   	
					<p style="line-height:18px;">${newsItem.updated_at}</p>
					<p>${newsItem.shortDesc}</p>
					<br/>
				</div>	
			</pg:item>
	
	    </c:forEach>

        <div class="pagination">    
	        <pg:index>
	            <pg:first><a href="<%= pageUrl %>">Prva</a></pg:first>
	            <pg:pages>
	            <%
	              if (pageNumber.intValue() < 10) { 
	                %>&nbsp;<%
	              }
	              if (pageNumber == currentPageNumber) { 
	                %><span class="current"><%= pageNumber %></span><%
	              } else { 
	                %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
	              }
	            %>
	            </pg:pages>
	            <pg:last><a href="<%= pageUrl %>">Zadnja</a></pg:last>
	        </pg:index>
        </div>

    </pg:pager>

    <c:if test="${varMode eq 'home'}">
        <div class="more-navigation-link">
	        <ul>
	            <li><a href="<c:out value="${varToAllNewsLink}" />" >Več novic</a></li>
	        </ul>
        </div>
    </c:if>
</div>
  