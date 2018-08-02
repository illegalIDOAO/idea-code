package com.kaishengit.serviceimpl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.PermissionMapper;
import com.kaishengit.mapper.RolePermissionMapper;
import com.kaishengit.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 16:04 2018/7/26
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 查询权限列表
     *
     * @return
     */
    @Override
    public List<Permission> selectAll() {
        PermissionExample permissionExample = new PermissionExample();
        List<Permission> permissionList =  permissionMapper.selectByExample(permissionExample);

        List<Permission> endList = new ArrayList<>();

        treeList(endList,permissionList,0);

        return endList;
    }
    /**
     * 将查询数据库的角色列表转换为树形集合结果
     * @param endList
     * @param sourceList
     * @param parentId
     */
    private void treeList(List<Permission> endList, List<Permission> sourceList, int parentId) {
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(sourceList, new Predicate<Permission>() {
            @Override
            public boolean apply(Permission permission) {
                return permission.getPid().equals(parentId);
            }
        }));
        for(Permission permission : tempList){
            endList.add(permission);
            treeList(endList,sourceList,permission.getId());
        }
    }

    /**
     * 查询菜单权限列表
     *
     * @return
     */
    @Override
    public List<Permission> selectMenuPermission() {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(Permission.PERMISSION_TYPE_MENU);
        return permissionMapper.selectByExample(permissionExample);
    }


    /**
     * 新增权限
     *
     * @param permission
     */
    @Override
    public void newPermission(Permission permission) {
        permission.setCreateTime(new Date());
        permissionMapper.insert(permission);
    }

    /**
     * 删除权限
     *
     * @param id
     * @throws NotAllowException
     */
    @Override
    public void del(int id) throws NotAllowException{
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(id);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList != null && permissionList.size() != 0){
            throw new NotAllowException("该权限下有子权限，拒绝删除");
        }

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPermissionIdEqualTo(id);
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);
        if(rolePermissionList != null && rolePermissionList.size() != 0){
            throw new NotAllowException("该权限已被角色使用，拒绝删除");
        }

        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据Id查找权限
     *
     * @param id
     * @return
     */
    @Override
    public Permission selectPermissionById(int id) {
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        return permission;
    }

    /**
     * 修改权限
     *
     * @param permission
     */
    @Override
    public void editPermission(Permission permission) {
        permission.setUpdateTime(new Date());
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 根据roleId查找permission
     *
     * @param id
     * @return
     */
    @Override
    public List<Permission> selectPermissionByRoleId(Integer id) {
        return permissionMapper.selectByRoleId(id);
    }
}
