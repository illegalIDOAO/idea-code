<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-员工详情</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@ include file="../include/header.jsp" %>
    <%--<%@ include file="../include/sider.jsp" %>--%>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                员工详情
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="panel panel-primary">
                <!-- Default panel contents -->
                <div class="panel-heading">员工信息</div>
                <!-- List group -->
                <ul class="list-group">
                    <li class="list-group-item">员工账号：${employee.employeeAccount}</li>
                    <li class="list-group-item">员工姓名：${employee.employeeName}</li>
                    <li class="list-group-item">员工电话：${employee.tel}</li>
                    <li class="list-group-item">员工权限：${employee.permissionId}</li>
                    <li class="list-group-item">员工部门：${employee.role.roleName}</li>
                    <li class="list-group-item">部门工作：${employee.role.roleCode}</li>
                </ul>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@ include file="../include/js.jsp" %>
<script>
    $(function(){

    })
</script>
</body>
</html>

