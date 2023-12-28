
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<html>
<head>
    <title>Title</title>
    <script>
        window.onload = function() {
            alert("${msg}");
            history.go(-1);
        }
    </script>
</head>
<body>

</body>
</html>
