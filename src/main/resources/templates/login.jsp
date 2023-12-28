<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
    <script>
        function formCheck(e){
            let userID = document.getElementById("userID").value;
            let password = document.getElementById("password").value;

            if(userID == null || userID === "") {
                e.preventDefault();
                alert('아이디를 입력하세요');
                return false;
            }
            if(password == null || password === "") {
                e.preventDefault();
                alert('비밀번호를 입력하세요');
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h1 style="margin-top: 200px;">로그인</h1>
    <form action="${pageContext.servletContext.contextPath}/loginOk" method="POST" onsubmit="return formCheck()">
        <label for="userid">userID</label>
        <input type="text" name="userid" id="userid">
        <label for="userpwd">password</label>
        <input type="password" name="userpwd" id="userpwd">
        <input type="submit" value="로그인">
    </form>
</body>
</html>
