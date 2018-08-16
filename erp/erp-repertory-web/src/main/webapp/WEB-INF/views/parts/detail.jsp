<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-配件详情</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- =============================================== -->
    <%@ include file="../include/css.jsp" %>
    <!-- =============================================== -->
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

    <!-- =============================================== -->
    <!-- 顶部导航栏部分 -->
    <%@ include file="../include/header.jsp" %>
    <!-- =============================================== -->
    <!-- 左侧菜单栏 -->
    <%--<%@ include file="../include/sider.jsp" %>--%>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                配件详情
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="panel panel-primary">
                <!-- Default panel contents -->
                <div class="panel-heading">${parts.partsName}</div>
                <!-- List group -->
                <ul class="list-group">
                    <li class="list-group-item">配件编号：${parts.partsNo}</li>
                    <li class="list-group-item">类型：${parts.type.typeName}</li>
                    <li class="list-group-item">进价：${parts.inPrice}</li>
                    <li class="list-group-item">售价：${parts.salePrice}</li>
                    <li class="list-group-item">库存：${parts.inventory}</li>
                    <li class="list-group-item">产地：${parts.address}</li>
                </ul>
            </div>

            <div class="box">
                <div class="box-body">
                    <a href="#" class="btn btn-success pull-right" data-toggle="modal"
                       data-target="#addModal">增加库存</a>
                </div>
            </div>
            <!-- /.box -->
            <!-- /.modal -->
            <div class="modal fade" id="addModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">入库</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/parts/addInventory" method="post" class="form-horizontal" id="addForm">
                                <input hidden name="partsId" value="${parts.id}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">入库数量:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control"
                                               name="addNum" placeholder="请输入新增数量"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary pull-left" id="addBtn">保存</button>
                            <button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->

        </section>
        <!-- /.content -->
    </div>

    <!-- =============================================== -->
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jsp" %>
    <!-- =============================================== -->

</div>
<!-- ./wrapper -->
<!-- =============================================== -->
<%@ include file="../include/js.jsp" %>
<!-- =============================================== -->

<script>
    $(function(){
        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        $("#addBtn").click(function(){
            $("#addForm").submit();
        });


    })
</script>
</body>
</html>

