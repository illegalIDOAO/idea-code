<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP | 账号管理</title>
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
        <jsp:param name="menu" value="manage_employee"/>
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
            <div class="box no-border">

                <div class="box-body">
                    <form class="form-inline">
                        <input type="text" name="accountNameTel" placeholder="账号、姓名或手机号码" class="form-control" value="${param.accountNameTel}">
                        <select name="roleId" class="form-control">
                            <option value="">所有账号</option>
                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}" ${param.roleId == role.id ? 'selected' : ''}>${role.roleName}</option>
                            </c:forEach>
                        </select>
                        <button class="btn btn-default">搜索</button>
                    </form>
                </div>
            </div>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">員工列表</h3>
                    <div class="box-tools">
                        <shiro:hasPermission name="employee:new">
                            <a href="/manage/employee/new" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 新增账号
                            </a>
                        </shiro:hasPermission>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr class="bg-purple-gradient">
                            <th>账号</th>
                            <th>姓名</th>
                            <th>手机号码</th>
                            <th>角色</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${employeeList}" var="employee">
                                <%--<tr class="${employee.state != 2 ? 'bg-gray' : 'bg-gray-light'}">--%>
                                <tr class="${employee.state == 0 ? 'bg-gray-active' : employee.state == 1 ? 'bg-gray' : 'bg-gray-light'}">
                                    <td>${employee.employeeAccount}</td>
                                    <td>${employee.employeeName}</td>
                                    <td>${employee.tel}</td>
                                    <td>
                                        <c:forEach items="${employee.roleList}" var="role">
                                            ${role.roleName}
                                        </c:forEach>
                                    </td>
                                    <td>
                                        ${employee.state == 0 ? "正常" : (employee.state == 1 ? "禁用" : "离职")}
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${employee.createTime}"/>
                                    </td>
                                    <td>
                                        <shiro:hasPermission name="employee:del">
                                            <a class="btn btn-primary btn-xs" href="/manage/employee/${employee.id}/edit" ${employee.state == 2 ? 'disabled':''} title="更改">
                                                <i class="fa fa-edit"></i></a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="employee:frozen">
                                            <a class="btn btn-warning btn-xs frozen" rel="${employee.id}" ${employee.state == 2 ? 'disabled':''} href="javascript:;" title="冻结/解冻">
                                                <i class="fa fa-${employee.state  == 0 ? 'unlock' : 'lock'}"></i></a>
                                        </shiro:hasPermission>
                                            <a class="btn"href="javascript:;"></a>
                                        <shiro:hasPermission name="employee:leave">
                                            <a class="btn btn-danger btn-xs leave" rel="${employee.id}" ${employee.state == 2 ? 'disabled':''} href="javascript:;" title="离职">
                                                <i class="fa fa-ban"></i></a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="employee:del">
                                            <a class="btn btn-danger btn-xs del" rel="${employee.id}" ${employee.state == 2 ? 'disabled':''} href="javascript:;" title="删除">
                                                <i class="fa fa-trash"></i></a>
                                        </shiro:hasPermission>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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
        $(".frozen").click(function(){
            var id = $(this).attr("rel");
            layer.confirm("确定要冻结/解冻账户么？",function() {
                $.get("/manage/employee/" + id + "/frozen").done(function(res){
                    if(res.state == "success") {
                        layer.msg("操作成功", {icon:1, time:1000},function () {
                            history.go(0);
                        });
                    } else {
                        layer.msg(res.message, {icon:2, time:2000});
                    }
                }).error(function() {
                    layer.msg("系统异常")
                })
            })
        });
        $(".leave").click(function(){
            var id = $(this).attr("rel");
            layer.confirm("确定该员工办理离职？",function() {
                $.get("/manage/employee/" + id + "/leave").done(function(res){
                    if(res.state == "success") {
                        layer.msg("办理成功", {icon:1, time:1000},function () {
                            history.go(0);
                        });
                    } else {
                        layer.msg(res.message, {icon:2, time:2000});
                    }
                }).error(function() {
                    layer.msg("系统异常")
                })
            })
        });
        $(".del").click(function(){
            var id = $(this).attr("rel");
            layer.confirm("确定要删除该员工账号？",function() {
                //window.location.href = "/manage/permission/" + id + "/del";
                $.get("/manage/employee/" + id + "/del").done(function(res){
                    if(res.state == "success") {
                        layer.msg("删除成功", {icon:1, time:1000},function () {
                            history.go(0);
                        });
                    } else {
                        layer.msg(res.message, {icon:2, time:2000});
                    }
                }).error(function() {
                    layer.msg("系统异常")
                })
            })
        });

    });

</script>
</body>
</html>
