<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: qwer
  Date: 2018/7/20
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h3>welcom,${type},${id}</h3>
    <form action="/user/add" method="post">
        <input type="text" name="userName"/>账户名</br>
        <input type="text" name="password"/>密码</br>
        <input type="text" name="tel"/>电话</br>
        <button>提交</button>
    </form>
</body>
</html>
