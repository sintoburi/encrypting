
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>

<html>
<head>
    <title>Title</title>
    <style>
        form, h1 {
            text-align: center;
        }
    </style>
</head>
<body>
    <h1 style="margin-top: 200px;">회원가입</h1>
    <form action="${pageContext.servletContext.contextPath}/signupOk" method="POST">
        <label for="userid">userID</label>
        <input type="text" name="userid" id="userid">
        <label for="userpwd">password</label>
        <input type="password" name="userpwd" id="userpwd">
        <input type="submit" value="회원가입">
    </form>
</body>
</html>
