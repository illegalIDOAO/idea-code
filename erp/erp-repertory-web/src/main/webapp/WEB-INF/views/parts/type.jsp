<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-类型管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                类型管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>类型id</th>
                            <th>类型名称</th>
                            <th>删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${typeList}" var="type">
                            <tr>
                                <td>${type.id}</td>
                                <td>${type.typeName}</td>
                                <td><a href="javascrapt:;" ref="${type.id}" class="delete">删除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
                <!-- /.box-body -->
            </div>

            <!-- /.box -->
            <div class="box">
                <div class="box-body">
                    <a href="#" class="btn btn-success pull-right" data-toggle="modal"
                       data-target="#addModal">新增类型</a>
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
                            <h4 class="modal-title">新增类型</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/type/new" method="post" class="form-horizontal" id="newForm">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">类型名称:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="newTypeName"
                                               name="typeName" placeholder="请输入类型名称">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary pull-left" id="newBtn">保存</button>
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
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp" %>


</div>
<!-- ./wrapper -->

<%@ include file="../include/js.jsp" %>
<script>
    $(function(){
        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        $(".delete").click(function(){
            var id = $(this).attr("ref");
            layer.confirm("确定删除？",function () {
                window.location.href = "/type/" + id + "/del";
            })
        });

        $("#newBtn").click(function(){
            $("#newForm").submit();
        });
        $("#addForm").validate({
            errorClass : "text-danger",
            errorElement : "span",
            rules : {
                typeName : {
                    required : true,
                }
            },
            messages : {
                typeName : {
                    required : "请输入类型编号",
                }
            }
        });

        var locale = {
            "format": 'YYYY-MM-DD',
            "separator": " - ",//
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1
        };

        var startDate = "";
        var endDate = "";

        if(startDate && endDate) {
            $('#time').val(startDate + " / " + endDate);
        }


        $('#time').daterangepicker({
            autoUpdateInput:false,
            "locale": locale,
            "opens":"right",
            "timePicker":false
        },function(start,end) {
            $("#startTime").val(start.format('YYYY-MM-DD'));
            $("#endTime").val(end.format('YYYY-MM-DD'));

            $('#time').val(start.format('YYYY-MM-DD') + " / " + end.format('YYYY-MM-DD'));
        });

    })
</script>
</body>
</html>
