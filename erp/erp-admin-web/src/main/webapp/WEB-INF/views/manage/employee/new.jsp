<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP | 新增账号</title>
    <!-- =============================================== -->
    <%@include file="../../include/css.jsp"%>
    <!-- =============================================== -->

</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- =============================================== -->
    <%@include file="../../include/header.jsp"%>
    <!-- =============================================== -->
    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_account"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                账号管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">新增账号</h3>
                </div>
                <div class="box-body">
                    <form method="post" id="saveForm">
                        <div class="form-group">
                            <label>账号</label>
                                <input type="text" class="form-control" name="employeeAccount">
                            </div><div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" name="employeeName">
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="tel">
                        </div>
                        <div class="form-group">
                            <label>密码(默认123)</label>
                            <input type="password" class="form-control" name="password" value="123">
                        </div>
                        <div class="form-group">
                            <label>角色</label>
                            <div>
                                <c:forEach items="${roleList}" var="role">
                                <div class="checkbox-inline">
                                    <input type="checkbox" value="${role.id}" name="roleIds"> ${role.roleName}
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary pull-right" id="saveBtn">保存</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <!-- =============================================== -->
    <%@include file="../../include/footer.jsp"%>
    <!-- =============================================== -->
</div>
<!-- ./wrapper -->

<!-- =============================================== -->
<%@include file="../../include/js.jsp"%>
<!-- =============================================== -->
<script>
    $(function () {
        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });
        $("#saveForm").validate({
            errorClass : "text-danger",
            errorElement : "span",
            rules : {
                employeeAccount : {
                    required : true,
                    remote : "/manage/employee/checkEmployeeAccount"
                },
                employeeName : {
                    required : true
                },
                password :{
                    required : true,
                    digits : true
                },
                tel :{
                    required : true,
                    number : true
                },
                roleIds :{
                    required : true
                },
            },
            messages : {
                employeeAccount : {
                    required : "请输入员工账号",
                    remote : "该账户已被占用"
                },
                employeeName : {
                    required : "请输入员工姓名"
                },
                password :{
                    required : "请输入密码"
                },
                tel :{
                    required : "请输入电话",
                    number : "请输入纯数字"
                },
                roleIds :{
                    required : true
                },
            }
        });
    });
</script>
</body>
</html>
