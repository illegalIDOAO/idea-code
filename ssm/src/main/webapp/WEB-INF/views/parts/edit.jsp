<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-库存管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                配件更新
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <form action="/parts/edit" method="post" id="addForm">
                        <div class="form-group">
                            <label class=" control-label">配件编号:</label>
                            <input type="text" disabled class="form-control" value="${parts.partsNo}" placeholder="请输入配件编号">
                        </div>
                        <div class="form-group">
                            <label>配件名称:</label>
                            <input type="hidden" name="id" value="${parts.id}" class="form-control" placeholder="请输入配件名称">
                            <input type="text" name="partsName" value="${parts.partsName}" class="form-control" placeholder="请输入配件名称">
                        </div>

                        <div class="form-group">
                            <label>进价:</label>
                            <input type="text" name="inPrice" value="${parts.inPrice}" class="form-control" placeholder="请输入进价">
                        </div>
                        <div class="form-group">
                            <label>售价:</label>
                            <input type="text" name="salePrice" value="${parts.salePrice}" class="form-control" placeholder="请输入售价">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2">类型:</label>
                            <select name="typeId" class="form-control">
                                <option>请选择类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.id}" ${parts.typeId == type.id ? 'selected': ''} >${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>产地:</label>
                            <input type="text" name="address" value="${parts.address}"  class="form-control" placeholder="请输入产地">
                        </div>
                    </form>
                    <button class="btn btn-primary pull-left" id="saveBtn">保存</button>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp" %>

</div>

<%@ include file="../include/js.jsp" %>
<script>

    $(function(){
        $("#saveBtn").click(function(){
           $("#addForm").submit();
        });
        $("#addForm").validate({
            errorClass : "text-danger",
            errorElement : "span",
            rules : {
                partsName : {
                    required : true
                },
                inPrice :{
                    required : true,
                    number : true,
                    min : 0
                },
                salePrice :{
                    required : true,
                    number : true,
                    min : 0
                },
                typeId :{
                    required : true
                },
                address :{
                    required : true
                }
            },
            messages : {
                partsName : {
                    required : "请输入类型名称"
                },
                inPrice :{
                    required : "请输入进价",
                    number : "请输入纯数字",
                    min: $.validator.format( "请输入不小于 {0} 的数值" )
                },
                salePrice :{
                    required : "请输入售价",
                    number : "请输入纯数字",
                    min: $.validator.format( "请输入不小于 {0} 的数值" )
                },
                typeId :{
                    required : "请选择类型"
                },
                address :{
                    required : "请输入地址"
                }
            }
        });
    });

</script>
</body>
</html>

