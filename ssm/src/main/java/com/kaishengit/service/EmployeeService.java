package com.kaishengit.service;

import com.kaishengit.entity.Employee;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:48 2018/7/25
 */
public interface EmployeeService {

    /**
     * 查询所有员工
     * @return
     */
    List<Employee> selectEmployeeList() ;

    /**
     * 登录判断
     * @param employee
     * @return Employee
     */
    Employee login(Employee employee);

    /**
     * 查询员工详细信息
     * @param id
     * @return Employee
     */
    Employee selectEmployeById(int id);
}
