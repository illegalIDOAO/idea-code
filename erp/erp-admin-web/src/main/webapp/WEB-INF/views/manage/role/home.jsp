<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 角色管理</title>
    <%@include file="../../include/css.jsp"%>
    <!-- =============================================== -->
    <link rel="stylesheet" href="/static/plugins/treegrid/css/jquery.treegrid.css">
    <!-- =============================================== -->
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- =============================================== -->
    <%@include file="../../include/header.jsp"%>
    <!-- =============================================== -->
    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_roles"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                角色管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">角色列表</h3>
                    <div class="box-tools">
                        <shiro:hasPermission name="role:new">
                            <a href="/manage/role/new" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i>新增角色</a>
                        </shiro:hasPermission>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table tree">
                        <tbody>
                            <c:forEach items="${roleList}" var="role">
                                <tr class="bg-gray-active">
                                    <td>
                                        角色名称：<strong>${role.roleName}</strong>
                                        <span class="pull-right">
                                            <shiro:hasPermission name="role:edit">
                                                <a style="color: #fff;" href="/manage/role/${role.id}/edit"><i class="fa fa-pencil"></i></a>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="role:del">
                                                <a style="color: #fff;" class="delLink" rel="${role.id}" href="javascript:;">
                                                    <i class="fa fa-trash"></i></a>
                                            </shiro:hasPermission>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <c:forEach items="${role.permissionList}" var="permission" varStatus="status">
                                            <c:if test="${permission.pid == 0 && status.count != 1}">
                                                <br>
                                            </c:if>
                                            <i class="fa fa-circle"></i> ${permission.permissionName}
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr><td></td></tr>
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
    <!-- 底部 -->
    <%@include file="../../include/footer.jsp"%>
    <!-- =============================================== -->
</div>
<!-- ./wrapper -->
<!-- =============================================== -->
<%@include file="../../include/js.jsp"%>
<script src="/static/plugins/treegrid/js/jquery.treegrid.min.js"></script>
<script src="/static/plugins/treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<!-- =============================================== -->
<script>
    $(function () {
        $('.tree').treegrid();

        var message = "${message}";
        if(message){
            layer.msg(message,{icon:6, time:1000},function(){
                history.go(0);
            });
        }

        //删除
        $(".delLink").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除该角色？",function (index) {
                layer.close(index);
                $.get("/manage/role/"+id+"/del").done(function (res) {
                    if(res.state == "success") {
                        layer.msg("删除成功", {icon:1, time:1000},function () {
                            history.go(0);
                        });
                    } else {
                        layer.msg(res.message, {icon:2, time:2000});
                    }
                }).error(function () {
                    layer.msg("服务器忙");
                });
            })
        });
    });
</script>
</body>
</html>
