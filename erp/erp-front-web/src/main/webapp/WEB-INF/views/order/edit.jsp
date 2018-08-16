<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-订单修改</title>

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
            <h1 style="text-align: center">
                保养维修单
            </h1>

            <div class="box-tools vis">
                <a href="/order/undone/list" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i>放弃修改，返回列表</a>
            </div>
        </section>

        <!-- Main content -->
        <section class="content">

            <%--车辆信息--%>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户车辆信息</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td class="td_title">车牌号:</td>
                            <td style="width: 150px">
                                <input type="text" class="form-control" v-model="car.licenceNo">
                            </td>
                            <td>
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-info btn-flat" @click="search"><i class="fa fa-search"></i></button>
                            </span>
                            </td>
                            <td class="td_title">车型:</td>
                            <td id="carType">{{car.carType}}</td>
                            <td class="td_title">车辆识别码:</td>
                            <td id="carNo">{{car.carNo}}</td>
                        </tr>
                        <tr>
                            <td class="td_title">客户姓名:</td>
                            <td id="userName">{{customer.userName}}</td>
                            <td></td>
                            <td class="td_title">身份证号:</td>
                            <td id="idCard">{{customer.idCard}}</td>
                            <td class="td_title">车主电话:</td>
                            <td id="tel">{{customer.tel}}</td>
                        </tr>
                    </table>
                </div>
            </div>

            <%--服务选择--%>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">项目选择</h3>
                </div>
                <div class="box-body">
                    <div class="form-inline">
                        <select class="form-control" v-model="serviceType">
                            <option value="">请选择项目</option>
                            <option v-for="item in serviceTypes" :value="item">{{item.serviceName}}--{{item.serviceHour}}工时</option>
                        </select>
                    </div>
                    <br>
                    <table class="table table-bordered " style="border-width: 2px;" id="infoForm">
                        <thead>
                        <tr>
                            <th>项目代码</th>
                            <th>项目名称</th>
                            <th>工时费用</th>
                        </tr>
                        </thead>
                        <tbody id="addTr">
                        <tr v-if="serviceType.serviceNo">
                            <td>{{serviceType.serviceNo}}</td>
                            <td>{{serviceType.serviceName}}</td>
                            <td>{{serviceType.serviceHour * 50}}</td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：{{hourPee}}元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <%--配件选择--%>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">选择配件</h3>
                </div>
                <div class="box-body">
                    <div class="form-inline">
                        <select class="form-control" v-model="partsType" @change="changeType">
                            <option value="">请选择配件类型</option>
                            <option v-for="item in partsTypes" :value="item">{{item.typeName}}</option>
                        </select>
                        <select class="form-control" v-model="targetParts">
                            <option value="">请选择配件</option>
                            <option v-for="item in partsList" :value="item">{{item.id}}-{{item.partsName}}-{{item.partsNo}}-{{item.salePrice}}</option>
                        </select>
                        <button class="btn btn-info" @click="addParts">选择</button>
                    </div>
                    <br>
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
                        <tr v-for="item in targetPartsList">
                            <td>{{item.partsNo}}</td>
                            <td>{{item.partsName}}</td>
                            <td>{{item.salePrice}}</td>
                            <td><button type="button" class="btn btn-box-tool" @click="minus(item)"><i class="fa fa-minus"></i></button>
                                <input type="text" class="num" v-model="item.num">
                                <button type="button" class="btn btn-box-tool" @click="plus(item)"><i class="fa fa-plus"></i></button></td>
                            <td><button class="btn btn-danger btn-sm" @click="delParts(item)"><i class="fa fa-minus"></i></button></td>
                        </tr>

                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：{{partsSum}}元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="box">
                <div class="box-header">
                    <h4>总计： {{fee}} 元</h4>
                </div>
            </div>

            <button class="btn btn-info btn-block btn-lg" @click="editOrder">提交修改</button>

            <!--模态框-->
            <div class="modal fade" tabindex="-1" role="dialog" id="addCarModal">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">添加信息</h4>
                        </div>
                        <div class="modal-body">
                            <%--<form id="addCarForm" action="/car/new" method="post" class="form">--%>
                            <form id="addCarForm" class="form">
                                <div class="form-group"><label>车辆信息：</label></div>
                                <div class="form-group">
                                    <label>车牌号码：</label>
                                    <input type="text" class="newCarLicence form-control" disabled v-model="car.licenceNo">
                                    <input type="text" class="newCarLicence" hidden name="licenceNo" v-model="car.licenceNo">
                                </div>
                                <div class="form-group">
                                    <label>车辆识别码：</label>
                                    <input type="text" name="carNo" id="newCarNo" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车辆型号(必填)：</label>
                                    <input type="text" name="carType" id="newCarType" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车辆颜色：</label>
                                    <input type="text" name="color" class="form-control" v-model="car.coler">
                                </div>
                                <div class="form-group"><label>车主信息：</label></div>
                                <div class="form-group">
                                    <label>车主身份证(必填)：</label>
                                    <input type="text" name="idCard" id="newIdCard" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车主姓名(必填)：</label>
                                    <input type="text" name="userName" id="newUserName" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车主电话：</label>
                                    <input type="text" name="tel" id="newTel" class="form-control">
                                </div>

                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" id="addCar" @click="addCarInfo">添加</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->

        </section>
        <!-- /.content -->


    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp" %>

</div>
<!-- ./wrapper -->
<%@ include file="../include/js.jsp" %>
<script src="/static/dist/js/vue.js"></script>
<script>

    $(function() {
        var orderId = ${orderId};
        var vm = new Vue({
            el: '#app',
            data: {
                id:{},
                car: {},
                customer: {},
                serviceTypes: [],
                serviceType:{},
                partsTypes: [],
                partsType:{},
                partsList: [],
                targetParts:{},
                targetPartsList:[],
            },
            mounted: function(){
                $.get("/order/service/getTypes").done(function(res){
                    vm.serviceTypes = res.data;
                }).fail(function(res){
                    layer.msg("系統異常");
                });
                $.get("/order/parts/types").done(function(res){
                    vm.partsTypes = res.data;
                }).fail(function(res){
                    layer.msg("系統異常");
                });
                $.get("/order/" + orderId + "/orderInfo").done(function(res){
                    vm.car = res.data.order.car;
                    vm.customer = res.data.order.customer;
                    vm.serviceType = res.data.serviceType;
                    vm.targetPartsList = res.data.partsList;
                }).fail(function(res){
                    layer.msg("系統異常");
                })
            },
            methods: {
                search: function() {
                    var licenceNo = vm.car.licenceNo;
                    if(licenceNo){
                        $.get("/car/check",{"licenceNo":licenceNo}).done(function(res){
                            if (res.state == 'success') {
                                vm.car = res.data;
                                vm.customer = res.data.customer;
                            } else {
                                // 如果不存在则打开模态框新增车辆信息
                                $("#addCarModal").modal({
                                    show:true,
                                    backdrop:'static'
                                });
                            }
                        }).fail(function(){
                            layer.msg("系统异常")
                        })
                    } else {
                        layer.msg("请填写车牌")
                    }
                },
                addCarInfo: function(){
                    if(!$("#newCarType").val()){
                        layer.msg("请输入车辆型号");
                        return;
                    }
                    if(!$("#newIdCard").val()){
                        layer.msg("请输入车主身份证号码");
                        return;
                    }
                    if(!$("#newUserName").val()){
                        layer.msg("请输入车主姓名");
                        return;
                    }
                    $.ajax({
                        url:"/car/new",
                        type:"post",
                        data:$("#addCarForm").serialize(),
                        success:function(res){
                            if(res.state == "success"){
                                vm.car = res.data;
                                vm.customer = res.data.customer;
                                $("#addCarModal").modal('hide');
                                layer.msg("新增成功",{time:1000})
                            } else {
                                layer.msg("新增失败",{time:1000});
                            }
                        },error:function(){
                            layer.alert("系统繁忙");
                        }
                    })
                },
                changeType: function(){
                    this.partsList = {}
                    this.targetParts = {}
                    if(this.partsType.id){
                        $.get("/order/"+ this.partsType.id +"/parts").done(function(res){
                            for(var i = 0; i < res.data.length; i++){
                                res.data[i].num = 1;
                            }
                            vm.partsList = res.data;
                        }).fail(function () {
                            layer.msg("系统故障");
                        })
                    }
                },
                addParts: function(){
                    var flag = true;
                    for(var i = 0; i < this.targetPartsList.length; i++){
                        if(this.targetParts.id == this.targetPartsList[i].id){
                            flag = false;
                            if(this.targetPartsList[i].num < this.targetPartsList[i].inventory) {
                                this.targetPartsList[i].num ++;
                            } else {
                                layer.msg("库存不足");
                            }
                            break;
                        }
                    }
                    if(flag){
                        if(this.targetParts.id){
                            this.targetPartsList.push(this.targetParts);
                        }
                    }
                },
                minus: function (item) {
                    if(item.num > 1){
                        item.num--;
                    }
                },
                plus: function (item) {
                    if(item.num < item.inventory) {
                        item.num++;
                    } else {
                        layer.msg("库存不足")
                    }
                },
                delParts: function(ele){
                    var index = this.targetPartsList.indexOf(ele);
                    this.targetPartsList.splice(index,1);
                },
                editOrder: function(){
                    if(!this.car.id){
                        layer.msg("请输入车辆信息");
                        return;
                    }
                    if(!this.serviceType.id){
                        layer.msg("请选择服务类型");
                        return;
                    }
                    if(!this.targetPartsList.length){
                        layer.msg("请选择配件");
                        return;
                    }
                    for(var i = 0; i< this.targetPartsList.length; i++) {
                        if (this.targetPartsList[i].num > this.targetPartsList[i].inventory) {
                            layer.msg("配件 " + this.targetPartsList[i].partsName + " 选取数量大于库存");
                            return;
                        }
                    }
                    var partsList = [];
                    for(var i = 0; i < this.targetPartsList.length; i++){
                        var parts = {};
                        parts.partsId= this.targetPartsList[i].id;
                        parts.num = this.targetPartsList[i].num;
                        partsList.push(parts);
                    }
                    $.ajax({
                        type:"post",
                        url:"/order/" + orderId + "/edit",
                        data:{
                            json:JSON.stringify({
                                "carId":vm.car.id,
                                "serviceTypeId":vm.serviceType.id,
                                "fee" :vm.fee,
                                "partsList":partsList,
                            })
                        },
                        success:function(res){
                            if(res.state == "success"){
                                /*window.location.href = "/order/undone/list";*/
                                window.location.href = "/order/" + orderId + "/detail";
                            }else{
                                layer.msg(res.message);
                            }
                        },
                        error: function () {
                            layer.msg("系统异常");
                        }
                    })
                }
            },
            computed: {
                hourPee: function(){
                    return this.serviceType.serviceHour ? this.serviceType.serviceHour * 50 : 0 ;
                },
                partsSum: function(){
                    var sum = 0;
                    var parts;
                    for(var i = 0;i < this.targetPartsList.length; i++){
                        parts = this.targetPartsList[i];
                        sum = sum + parts.salePrice * parts.num;
                    }
                    return sum;
                },
                fee: function(){
                    return (this.hourPee + this.partsSum);
                }
            }
        })
    })
</script>
</body>
</html>
