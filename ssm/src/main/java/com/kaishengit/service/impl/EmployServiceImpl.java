package com.kaishengit.service.impl;

import com.kaishengit.entity.Employee;
import com.kaishengit.entity.EmployeeExample;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.EmployeeMapper;
import com.kaishengit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:48 2018/7/25
 */
@Service
public class EmployServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     *
     * @return
     */
    @Override
    public List<Employee> selectEmployeeList() {
        /*EmployeeExample employeeExample = new EmployeeExample();
        return employeeMapper.selectByExample(employeeExample);*/

        List<Employee> employeeList = employeeMapper.selectEmployeeList();
        return employeeList;
    }

    /**
     * 登录判断
     *
     * @param employee
     * @return Employee
     */
    @Override
    public Employee login(Employee employee) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria()
                .andEmployeeAccountEqualTo(employee.getEmployeeAccount())
                .andPasswordEqualTo(employee.getPassword());

        List<Employee> employees = employeeMapper.selectByExample(employeeExample);
        if(employees != null && employees.size() != 0){
            employee = employees.get(0);
            if(employee.getState() == 0){
                return employee;
            }else {
                throw new NotAllowException("用户名状态异常，拒绝登录");
            }
        }else{
            throw new NotAllowException("用户名或密码错误，拒绝登录");
        }
    }

    /**
     * 查询员工详细信息
     *
     * @param id
     * @return
     */
    @Override
    public Employee selectEmployeById(int id) {
        return employeeMapper.selectDetail(id);
    }
}
