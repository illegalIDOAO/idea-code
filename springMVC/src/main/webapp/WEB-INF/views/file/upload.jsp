<%--
  Created by IntelliJ IDEA.
  User: qwer
  Date: 2018/7/23
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
    <h2>文件上传</h2>
    <c:if test="${not empty message}">
        <div>${message}</div>
    </c:if>
    <span>${message}</span>

    <form action="/file/upload" method="post" enctype="multipart/form-data">
        <input type="text" name="fileName"> <br>
        <input type="file" name="file"/> <br>
        <button>upload</button>
    </form>

    <a href="http://localhost/file/download2?filename=1.jpg">download</a>

    <img src="http://localhost/file/download">


</body>
</html>
