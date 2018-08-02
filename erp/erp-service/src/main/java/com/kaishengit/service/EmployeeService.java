package com.kaishengit.service;

import com.kaishengit.entity.Employee;
import com.kaishengit.entity.EmployeeLoginLog;

import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:48 2018/7/25
 */
public interface EmployeeService {


    /**
     * 检查employeeAccount是否可用
     * @param employeeAccount
     */
    void check(String employeeAccount);

    /**
     * 查询所有员工
     * @return
     * @param paramMap
     */
    List<Employee> selectEmployeeList(Map<String,Object> paramMap) ;

    /**
     * 查询员工信息
     * @param id
     * @return
     */
    Employee selectEmployeById(int id);

    /**
     * 新增员工
     * @param employee
     * @param roleIds
     */
    void insert(Employee employee, Integer[] roleIds);

    /**
     *
     * @param employee
     * @param roleIds
     */
    void editEmployee(Employee employee, Integer[] roleIds);

    /**
     * 冻结账户
     * @param id
     */
    void frozenEmploee(Integer id);

    /**
     * 离职
     * @param id
     */
    void leaveEmployee(Integer id);

    /**
     * 删除账号
     * @param id
     */
    void delEmployee(Integer id);

    /**
     * 查找账户
     * @param account
     * @return
     */
    Employee selectEmployeByAccount(String account);

    /**
     * 添加登录日志
     * @param employeeLoginLog
     */
    void saveLogginLog(EmployeeLoginLog employeeLoginLog);


}
