<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-订单列表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <!-- =============================================== -->
    <%@include file="../include/css.jsp"%>
    <!-- =============================================== -->

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- =============================================== -->
    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->
    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                出库单查询
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box no-border">
                <div class="box-body">
                    <form class="form-inline" action="/parts/partsOutList">
                        <input type="text" name="orderId" placeholder="订单号" value="${param.orderId}" class="form-control">
                        <input type="text" name="partsNo" placeholder="配件编号" value="${param.partsNo}" class="form-control">
                        <input type="hidden" name="startTime" value="${param.startTime}" id="startTime">
                        <input type="hidden" name="endTime" value="${param.endTime}" id="endTime">
                        <input type="text" class="form-control" id="time" placeholder="下单日期选择">
                        <button class="btn btn-default">搜索</button>
                    </form>
                </div>
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>配件编码</th>
                            <th>配件名称</th>
                            <th>出库前数量</th>
                            <th>出库数量</th>
                            <th>余量</th>
                            <th>出库时间</th>
                            <th>取件员</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="partsStream">
                            <tr>
                                <td>${partsStream.orderId}</td>
                                <td>${partsStream.parts.partsNo}</td>
                                <td>${partsStream.parts.partsName}</td>
                                <td>${partsStream.preInventory}</td>
                                <td>${partsStream.num}</td>
                                <td>${partsStream.afterInventory}</td>
                                <td><fmt:formatDate value="${partsStream.createTime}"/></td>
                                <td>${partsStream.employeeName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- =============================================== -->
    <!-- 底部 -->
    <%@include file="../include/footer.jsp"%>
    <!-- =============================================== -->
</div>
<!-- ./wrapper -->
<!-- =============================================== -->
<%@include file="../include/js.jsp"%>
<!-- =============================================== -->
<script>
    $(function(){
        $("#pagination").twbsPagination({
            totalPages : ${page.pages},
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}&orderId=${param.orderId}&partsNo=${param.partsNo}&startTime=${param.startTime}&endTime=${param.endTime}"
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

        var startDate = "${param.startTime}";
        var endDate = "${param.endTime}";
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

