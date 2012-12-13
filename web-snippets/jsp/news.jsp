<!DOCTYPE html>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" info="Main index page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="indexPageBean" scope="request" class="com.gostinec.PageSessionBean">
  <jsp:setProperty name="indexPageBean" property="request" value="<%= request %>" />
  <jsp:setProperty name="indexPageBean" property="context" value="<%= application %>" />
  <jsp:setProperty name="indexPageBean" property="sectionName" value="novice" />
</jsp:useBean>
<% 
boolean isProcessed = indexPageBean.process();
if (isProcessed){
    pageContext.setAttribute("varNewsId", indexPageBean.getVarNewsId());
 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>gostinec.si :: prvi gostinski portal</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheets/colorbox.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/main.css" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/init.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/site.js"></script>
<script>
    var CONTEXT_PATH = '<%=request.getContextPath()%>';
</script>
</head>
<body>
    <div class="header-full-height centered">
        <div class="header-logo">
            <a href="<%=request.getContextPath()%>/home.jsp" title="Home page"><img height="94" src="images/header-logo.png" width="204" />
            </a>
        </div>
        <div class="header-title" style="width: 440px;">
            <jsp:include page="WEB-INF/components/banner/bHeader.jsp">
                <jsp:param name="section" value="7" />
            </jsp:include>
        </div>
        <jsp:include page="WEB-INF/components/login.jsp" />
        <jsp:include page="WEB-INF/components/toolbar.jsp">
            <jsp:param name="section" value="novice" />
        </jsp:include>
        <jsp:include page="WEB-INF/components/searchBox.jsp">
            <jsp:param name="sekcijaId" value="7" />
        </jsp:include>
        <div class="register-container">
            <a href="#inline_content" class="iframe"> <img height="142" src="images/register-image-link.png" width="189" /> </a>
        </div>
    </div>
    <div class="body-container centered">
        <div class="odmik">
            <div class="body-left-bg"></div>
            <div class="left-column">
                <jsp:include page="WEB-INF/components/categoriesUseful.jsp" />
                <div class="content-title">E-NOVICE</div>
                <div class="content" style="text-align: center; padding: 10px 25px; text-align: center">
                    <a href="#inline_content" class="iframeNovice suppliers-categories-link">PRIJAVITE SE NA E-NOVICE</a> <br /> <br />
                </div>
            </div>
            <div class="main-column">
                <div id="divContent" class="content-column" style="padding-top: 15px;">
                    <jsp:include page="WEB-INF/components/navigation.jsp" />
                    <c:choose>
                        <c:when test="${!empty varNewsId}">
                            <jsp:include page="WEB-INF/components/details/news_data.jsp">
                                <jsp:param name="paramNewsId" value="${varNewsId}" />
                            </jsp:include>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="WEB-INF/components/lists/news.jsp">
                                <jsp:param name="limit" value="500" />
                                <jsp:param name="mode" value="full" />
                            </jsp:include>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="right-column">
                 <div class="content-banner">
                        <jsp:include page="WEB-INF/components/banner/bRigth.jsp">
                            <jsp:param name="section" value="7" />
                        </jsp:include>
                    </div>
            </div>
            <div class="body-right-bg"></div>
        </div>
    </div>
    <div class="footer-container centered">
        <div class="links">
            <a href="<%=indexPageBean.getVarDocumentsPath()%>/splosni_pogoji.pdf" class="footer-link" target="_blank">Splošni pogoji</a>
            <a href="<%=indexPageBean.getVarDocumentsPath()%>/oglasevanje.pdf" class="footer-link" target="_blank">Oglaševanje</a>
            <a href="<%=indexPageBean.getVarDocumentsPath()%>/pogosta_vprasanja.pdf" class="footer-link" target="_blank">Pogosta vprašanja</a>
            <a href="<%=request.getContextPath()%>/contact.jsp" class="footer-link">Kontakt</a>
        </div>
        <div align="center" class="flv-bottom">
            <jsp:include page="WEB-INF/components/banner/bFooter.jsp">
                <jsp:param name="section" value="7" />
            </jsp:include>
        </div>
        <div align="center" class="copyright">
            Portal <a href="http://www.gostinec.si" style="color:#BE4F00">Gostinec.si</a>. 2012 ©  Vse pravice pridržane.
            Poganja <a style="color:#BE4F00" href="http://www.jocskupina.si">Joc Skupina d.o.o.</a>
        </div>
    </div>
</body>
<%
} else {
    response.sendRedirect("/error.jsp");
    return;
}
%>
</html>