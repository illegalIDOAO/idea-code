<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">

            <shiro:hasPermission name="system:manage">
                <li class="header">系统管理</li>
                <!-- 部门员工管理 -->
                <shiro:hasPermission name="manage:employee">
                    <li class="${param.menu == 'employee'? 'active' : ''}">
                        <a href="/manage/employee"><i class="fa fa-users"></i><span>员工管理</span></a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="manage:role">
                    <li>
                        <a href="/manage/role"><i class="fa fa-users"></i> <span>角色管理</span></a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="manage:permission">
                    <li>
                        <a href="/manage/permission"><i class="fa fa-users"></i> <span>权限管理</span></a>
                    </li>
                </shiro:hasPermission>
            </shiro:hasPermission>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
    