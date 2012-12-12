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
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script>
    var USER = '<%=pageBean.getUsername()%>';
</script>
</head>
<body>
  
  Logged in <%=pageBean.getUsername()%>
    
</body>
<%
	} else {
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return;
	}
%>
</html>