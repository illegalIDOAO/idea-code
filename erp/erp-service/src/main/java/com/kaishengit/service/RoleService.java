package com.kaishengit.service;

import com.kaishengit.entity.Role;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 16:03 2018/7/26
 */
public interface RoleService {

    /**
     * 查询所有角色列表
     * @return
     */
    List<Role> selectAll();

    /**
     * 新增角色
     * @param role
     * @param permissionIds
     */
    void newRole(Role role, Integer[] permissionIds);

    /**
     * s删除角色
     * @param roleId
     */
    void delRole(int roleId);

    /**
     * 根据id查找role
     * @param id
     * @return role
     */
    Role selectById(int id);

    /**
     * 跟新role
     * @param role
     * @param permissionIds
     */
    void editRole(Role role,Integer[] permissionIds);
}
