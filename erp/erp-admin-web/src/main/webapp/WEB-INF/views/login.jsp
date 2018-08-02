<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <%@ include file="include/css.jsp"%>

</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <b>汽车售后服务平台</b>
    </div>
    <!-- /.login-logo -->

    <div class="login-box-body">
        <form method="post" id="loginForm">
            <div class="form-group has-feedback">
                <input type="email" name="employeeAccount" class="form-control" placeholder="请输入用户名">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="请输入密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember" value="remember"> 记住账号
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <!-- /.col -->
            </div>
        </form>
        <button type="button" id="btn" class="btn btn-primary btn-block btn-flat">登录</button>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<%@ include file="include/js.jsp"%>
<script>
    $(function(){
        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        $("#btn").click(function(){
            $("#loginForm").submit();
        })
    })
</script>
</body>
</html>

