<!DOCTYPE html>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.portal.SessionUser"%>
<jsp:useBean id="pageBean" scope="request" class="com.portal.PageBean">
    <jsp:setProperty name="pageBean" property="request" value="<%= request %>" />
    <jsp:setProperty name="pageBean" property="context" value="<%= application %>" />
</jsp:useBean>
<%
boolean ok = pageBean.process();
if (ok) {
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Portal</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/home.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/init.js"></script>
<script src="<%=request.getContextPath()%>/home.js"></script>
<script>
    var USER = '<%=pageBean.getUsername()%>',
        CONTEXT_PATH = '<%=request.getContextPath()%>';
</script>
</head>
<body>
  
    <div class="menu header">
        <div class="logout"></div>
    </div>
    
    
    <h1>Logged in <%=pageBean.getUsername()%></h1>
    
    <div class="branches">
    
    <table class="grid">
        <caption>Some users</caption>
        <thead>
        <tr>
            <th>User ID</th>
            <th>User name</th>
            <th>Action to take</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    
    <div class="actions">
        <button class='removeAll'>Remove all</button>
        <button class='addNew'>Add new</button>
    </div>
    
    </div>
    
    <div class="menu footer">
      
    </div>
   
    
</body>
<%
	} else {
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return;
	}
%>
</html>