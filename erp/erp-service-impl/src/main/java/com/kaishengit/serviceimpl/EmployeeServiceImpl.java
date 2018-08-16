package com.kaishengit.serviceimpl;

import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.EmployeeLoginLogMapper;
import com.kaishengit.mapper.EmployeeMapper;
import com.kaishengit.mapper.EmployeeRoleMapper;
import com.kaishengit.service.EmployeeService;
import com.kaishengit.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:48 2018/7/25
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 查询所有员工
     *
     * @return
     * @param paramMap
     */
    @Override
    public List<Employee> selectEmployeeList(Map<String, Object> paramMap) {
        return  employeeMapper.findAllWithRolesByQueryParam(paramMap);
    }

    /**
     * 查询员工信息
     *
     * @param id
     * @return
     */
    @Override
    public Employee selectEmployeById(int id) {
        /*return employeeMapper.selectByPrimaryKey(id);*/
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return employeeMapper.selectEmployeeWithRole(paramMap);
    }

    /**
     * 检查employeeAccount是否可用
     *
     * @param employeeAccount
     */
    @Override
    public void check(String employeeAccount) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeAccountEqualTo(employeeAccount);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        if(employeeList.size() != 0){
            throw new NotAllowException("该账户已被占用");
        }
    }

    /**
     * 新增员工
     *
     * @param employee
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(Employee employee, Integer[] roleIds) {
        employee.setPassword(DigestUtils.md5Hex(employee.getPassword() + Constant.ADMIN_PASSWORD_SALT));
        employee.setState(Employee.EMPLOYEE_STATE_NORMAL);
        employeeMapper.insertSelective(employee);

        for(Integer roleId : roleIds){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setRoleId(roleId);
            employeeRole.setEmployeeId(employee.getId());
            employeeRoleMapper.insert(employeeRole);
        }

    }

    /**
     * @param employee
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editEmployee(Employee employee, Integer[] roleIds) {
        employeeMapper.updateByPrimaryKeySelective(employee);

        EmployeeRoleExample employeeRoleExample  = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(employee.getId());
        employeeRoleMapper.deleteByExample(employeeRoleExample);

        for(Integer roleId : roleIds){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(roleId);

            employeeRoleMapper.insert(employeeRole);
        }
    }

    /**
     * 冻结账户
     *
     * @param id
     */
    @Override
    public void frozenEmploee(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee == null){
            throw new NotAllowException("操作异常");
        } else if (employee.getState().equals(Employee.EMPLOYEE_STATE_NORMAL)) {
            employee.setState(Employee.EMPLOYEE_STATE_FROZEN);
            employeeMapper.updateByPrimaryKeySelective(employee);
        } else if (employee.getState().equals(Employee.EMPLOYEE_STATE_FROZEN)) {
             employee.setState(Employee.EMPLOYEE_STATE_NORMAL);
            employeeMapper.updateByPrimaryKeySelective(employee);
        } else {
             throw new NotAllowException("该员工已离职");
        }
    }

    /**
     * 离职
     *
     * @param id
     */
    @Override
    public void leaveEmployee(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee == null){
            throw new NotAllowException("操作异常");
        }
        long now =  (new Date()).getTime();
        long creatTime = (employee.getCreateTime().getTime());
        if(now - creatTime < 60*60*1000){
            throw new NotAllowException("该员工未办理档案，请直接删除");
        }
        employee.setState(Employee.EMPLOYEE_STATE_LEAVE);
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 删除账号
     *
     * @param id
     */
    @Override
    public void delEmployee(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee == null){
            throw new NotAllowException("操作异常");
        }
        long now =  (new Date()).getTime();
        long creatTime = (employee.getCreateTime().getTime());
        if(now - creatTime > 60*60*1000){
            throw new NotAllowException("该员工已办理档案，无法删除");
        }
        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查找账户
     * @param account
     * @return
     */
    @Override
    public Employee selectEmployeByAccount(String account) {
        /*EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeAccountEqualTo(account);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        if(employeeList != null && employeeList.size() != 0){
            return employeeList.get(0);
        }*/

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("account",account);
        Employee employee = employeeMapper.selectEmployeeWithRole(paramMap);
        if (employee != null){
            return employee;
        }
        return null;
    }

    /**
     * 添加登录日志
     *
     * @param employeeLoginLog
     */
    @Override
    public void saveLogginLog(EmployeeLoginLog employeeLoginLog) {
        employeeLoginLogMapper.insertSelective(employeeLoginLog);
    }

}
