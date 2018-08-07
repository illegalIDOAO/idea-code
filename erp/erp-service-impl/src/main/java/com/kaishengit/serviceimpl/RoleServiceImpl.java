package com.kaishengit.serviceimpl;

import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.EmployeeMapper;
import com.kaishengit.mapper.EmployeeRoleMapper;
import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.RolePermissionMapper;
import com.kaishengit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 16:02 2018/7/26
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 查询所有角色列表
     *
     * @return
     */
    @Override
    public List<Role> selectAll() {
        /*RoleExample roleExample = new RoleExample();
        return roleMapper.selectByExample(roleExample);*/

        return roleMapper.selectRoleWithPermission();
    }

    /**
     * 新增角色
     *
     * @param role
     * @param permissionIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void newRole(Role role, Integer[] permissionIds) {
        roleMapper.insertSelective(role);

        if(permissionIds != null){
            for(Integer permissionId : permissionIds){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(role.getId());
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }

    /**
     * 删除角色
     *
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRole(int roleId) {
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectByExample(employeeRoleExample);
        if(employeeRoleList != null && employeeRoleList.size() != 0){
            for(EmployeeRole employeeRole : employeeRoleList){
                if(employeeMapper.selectByPrimaryKey(employeeRole.getEmployeeId()).getState() != 3){
                    throw new NotAllowException("该角色已被使用，拒绝删除");
                }
            }
        }

        roleMapper.deleteByPrimaryKey(roleId);
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionMapper.deleteByExample(rolePermissionExample);
    }

    /**
     * 根据id查找role
     *
     * @param id
     * @return role
     */
    @Override
    public Role selectById(int id) {
        Role role = roleMapper.selectRoleWithPermissionById(id);
        return role;
    }

    /**
     * 跟新role
     *
     * @param role
     * @param permissionIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editRole(Role role,Integer[] permissionIds) {

        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionMapper.deleteByExample(rolePermissionExample);

        if(permissionIds != null){
            for(Integer permissionId : permissionIds){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }
}
