<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-订单详情</title>

    <style>
        .td_title {
            font-weight: bold;
        }

        .table>tbody>tr>td {
            vertical-align: middle;
        }

    </style>
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="order"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper" id="app">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="box-header with-border">
                <h3 class="box-title">保养维修单 -- 单号:N${order.id}</h3>
                <div class="box-tools vis">

                    <c:if test="${order.state == '6'}">
                        <a href="/order/done/list" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </c:if>
                    <c:if test="${order.state != '6'}">
                        <a href="/order/undone/list" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </c:if>
                    <button class="btn bg-gray-active btn-sm" id="printBtn"><i class="fa fa-print"></i> 打印</button>
                    <c:if test="${order.state == '0'}">
                        <shiro:hasPermission name="order:edit">
                            <a href="/order/${order.id}/edit" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 修改订单</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="order:trans">
                            <button class="btn btn-danger btn-sm" id="transBtn"><i class="fa fa-trash-o"></i> 订单下发</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="order:del">
                            <button class="btn btn-danger btn-sm" id="delBtn"><i class="fa fa-trash-o"></i> 删除</button>
                        </shiro:hasPermission>
                    </c:if>
                </div>
            </div>
        </section>

        <!-- Main content -->
        <section class="content">

            <h3 class="box-title visible-print-block">保养维修单 -- 单号:N${order.id}</h3>

            <div class="box">
                <div class="bo-body">
                    <table class="table">
                        <tr>
                            <td class="td_title">订单号:</td>
                            <td>${order.id}</td>
                            <td class="td_title">订单日期:</td>
                            <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"></fmt:formatDate> </td>

                        </tr>
                        <tr>
                            <td class="td_title">订单金额:</td>
                            <td>￥${order.orderMoney}</td>
                            <td class="td_title">订单状态:</td>
                            <td>${order.stateMean}</td>
                        </tr>

                    </table>
                </div>
            </div>

            <%--车辆信息--%>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户车辆信息</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td class="td_title">车牌号:</td>
                            <td>${order.car.licenceNo}</td>
                            <td class="td_title">车型:</td>
                            <td id="carType">${order.car.carType}</td>
                            <td class="td_title">车辆识别码:</td>
                            <td id="carNo">${order.car.carNo}</td>
                        </tr>
                        <tr>
                            <td class="td_title">客户姓名:</td>
                            <td id="userName">${order.customer.userName}</td>
                            <td class="td_title">身份证号:</td>
                            <td id="idCard">${order.customer.idCard}</td>
                            <td class="td_title">车主电话:</td>
                            <td id="tel">${order.customer.tel}</td>
                        </tr>
                    </table>
                </div>
            </div>

            <%--服务信息--%>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">服务项目</h3>
                </div>
                <div class="box-body">
                    <table class="table table-bordered " style="border-width: 2px;" id="infoForm">
                        <thead>
                        <tr>
                            <th>项目代码</th>
                            <th>项目名称</th>
                            <th>工时费用</th>
                        </tr>
                        </thead>
                        <tbody id="addTr">
                        <tr >
                            <td>${serviceType.serviceNo}</td>
                            <td>${serviceType.serviceName}</td>
                            <td v-model="hourPee">${serviceType.serviceHour * 50}</td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：${serviceType.serviceHour * 50} 元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <%--配件信息--%>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">使用配件</h3>
                </div>
                <div class="box-body">
                    <table class="table table-bordered " style="border-width: 2px;" id="partsInfoForm">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>单价</th>
                            <th>数量</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${partsList}" var="parts">
                                <tr>
                                    <td>${parts.partsNo}</td>
                                    <td>${parts.partsName}</td>
                                    <td>${parts.salePrice}</td>
                                    <td>${parts.num}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：${order.orderMoney - serviceType.serviceHour * 50} 元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="box">
                <div class="box-header">
                    <h4>总计： ${order.orderMoney} 元</h4>
                </div>
            </div>
                <c:if test="${order.state == '0'}">
                    <h4 class="pull-right visible-print-block" style="margin-right: 200px;">客户签字：</h4>
                </c:if>
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
        var orderId = ${order.id};
        $("#delBtn").click(function(){
            layer.confirm("确定删除订单？",function(){
                window.location.href="/order/"+orderId+"/del";
            })
        });
        $("#transBtn").click(function(){
            layer.confirm("订单下发后不能修改/删除,确定下发？",function(){
                $.get("/order/"+orderId+"/trans").done(function(res){
                    if(res.state = "success"){
                        layer.msg("下发成功",{icon:1,time:1000},function(){
                            history.go(0);
                        })
                    }else{
                        layer.msg(res.message);
                    }
                }).error(function(res){
                    layer.msg("系统异常")
                })
            })
        });

        $("#printBtn").click(function(){
            window.print();
        });

    })

</script>

</body>
</html>
