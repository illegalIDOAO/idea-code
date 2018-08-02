<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP | 修改账号</title>
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
                    <h3 class="box-title">修改账号</h3>
                </div>
                <div class="box-body">
                    <form method="post" action="/manage/employee/edit" id="saveForm">
                        <div class="form-group">
                            <label>账号</label>
                            <input type="text" hidden value="${employee.id}" name="id">
                            <input type="text" class="form-control" value="${employee.employeeAccount}" name="employeeAccount">
                        </div>
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" value="${employee.employeeName}" name="employeeName">
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" value="${employee.tel}" name="tel">
                        </div>
                        <div class="form-group">
                            <label>角色</label>
                            <div>
                                <c:forEach items="${roleList}" var="role" varStatus="flag">
                                    <c:set var="flag" value="false"/>
                                    <c:forEach items="${employee.roleList}" var="empRole">
                                        <c:if test="${role.id == empRole.id}">
                                            <c:set var="flag" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                <div class="checkbox-inline">
                                    <input type="checkbox" value="${role.id}" name="roleIds" ${flag ? 'checked': ''}> ${role.roleName}
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
                    remote : "/manage/employee/checkEmployeeAccount2?oldAccount=${employee.employeeAccount}"
                },
                employeeName : {
                    required : true
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
