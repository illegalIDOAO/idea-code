<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <%@include file="../include/css.jsp"%>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

  <%@ include file="../include/header.jsp"%>
    <!-- =============================================== -->

  <%@include file="../include/sider.jsp"%>
    <!-- =============================================== -->
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">员工管理</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool"  title="Collapse">
                            <i class="fa fa-plus"></i> 添加员工</button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>部门</th>
                            <th>手机</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${employeeList}" var="employee">
                            <tr>
                                <td><a href="/employee/${employee.id}/profile/">${employee.employeeName}</a></td>
                                <td>${employee.role.roleName}</td>
                                <td>${employee.tel}</td>
                                <td>
                                    <a href="">禁用</a>
                                    <a href="">删除</a>
                                    <a href="">编辑</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

 <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script>
    $(function(){

    });
</script>
</body>
</html>

