<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" isThreadSafe="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
  </head>
  <body>

  <%
      long threadId = Thread.currentThread().getId();
      out.println("Thread ID is" + threadId);

      int hash = this.hashCode();
      out.println("Hash code is" + hash);
  %>

  <jsp:include page="cached.jsp" />

   <p>hello </p>

  </body>
</html>