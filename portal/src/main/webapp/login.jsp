<!DOCTYPE html>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" info="Registration page for users : handles validation, processing, mail send"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Login</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/register.js"></script>
</head>
<body>
<form action="Login" method="post">
<table class="enter">
    <tbody>
        <tr>
            <td><label for="ime">Ime</label>:</td>
            <td><input id="ime" name="ime" type="text" /></td>
        </tr>
        <tr>
            <td><label for="geslo">Geslo</label>:</td>
            <td><input id="geslo" name="geslo" type="password" /></td>
        </tr>
        <tr>
            <td colspan="2"><input id="register" type="submit" value="Prijava" /></td>
        </tr>
    </tbody>
</table>
</form>
</body>
</html>