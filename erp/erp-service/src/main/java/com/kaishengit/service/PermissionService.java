package com.kaishengit.service;

import com.kaishengit.entity.Permission;
import com.kaishengit.exception.NotAllowException;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 16:03 2018/7/26
 */
public interface PermissionService {

    /**
     * 查询全部权限列表
     * @return
     */
    List<Permission> selectAll();

    /**
     * 查询菜单权限列表
     * @return
     */
    List<Permission> selectMenuPermission();

    /**
     * 新增权限
     * @param permission
     */
    void newPermission(Permission permission);

    /**
     * 删除权限
     * @param id
     * @throws NotAllowException
     */
    void del(int id) throws NotAllowException;

    /**
     * 根据Id查找权限
     * @param id
     * @return
     */
    Permission selectPermissionById(int id);

    /**
     * 修改权限
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 根据roleId查找permission
     * @param id
     * @return
     */
    List<Permission> selectPermissionByRoleId(Integer id);
}
