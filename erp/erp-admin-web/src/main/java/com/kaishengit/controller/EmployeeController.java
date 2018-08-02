package com.kaishengit.controller;

import com.kaishengit.controller.controllerExceptionHandeler.NotFountException;
import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.Employee;
import com.kaishengit.entity.Role;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.EmployeeService;
import com.kaishengit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:44 2018/7/25
 */
@Controller
@RequestMapping("/manage/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/{id:\\d+}/profile")
    public String profile(@PathVariable int id, Model model){
        Employee employee = employeeService.selectEmployeById(id);
        if(employee == null){
            throw new NotFountException("资源未找到");
        }
        model.addAttribute("employee",employee);
        return "manage/employee/profile";
    }

    @GetMapping()
    public String employeeList(@RequestParam(required = false) String accountNameTel,
                               @RequestParam(required = false) Integer roleId,
                               Model model){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("roleId",roleId);
        paramMap.put("accountNameTel",accountNameTel);
        List<Employee> employeeList = employeeService.selectEmployeeList(paramMap);
        model.addAttribute("employeeList", employeeList);

        List<Role> roleList = roleService.selectAll();
        model.addAttribute("roleList", roleList);
        return "manage/employee/home";
    }

    @GetMapping("/new")
    public String newEmployee(Model model){
        List<Role> roleList = roleService.selectAll();
        model.addAttribute("roleList",roleList);
        return "manage/employee/new";
    }

    @GetMapping("/checkEmployeeAccount")
    @ResponseBody
    public String checkEmployeeAccount(String employeeAccount){
        try{
            employeeService.check(employeeAccount);
            return "true";
        }catch(NotAllowException e){
            return "false";
        }
    }
    @GetMapping("/checkEmployeeAccount2")
    @ResponseBody
    public String checkEmployeeAccount2(String employeeAccount,String oldAccount){
        if(oldAccount.equals(employeeAccount)){
            return "true";
        }
        try{
            employeeService.check(employeeAccount);
            return "true";
        }catch(NotAllowException e){
            return "false";
        }
    }

    @PostMapping("/new")
    public String newEmployee(Employee employee ,Integer[] roleIds,RedirectAttributes redirectAttributes){
        employeeService.insert(employee,roleIds);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/manage/employee";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editEmployee(@PathVariable int id, Model model){
        Employee employee = employeeService.selectEmployeById(id);
        List<Role> roleList = roleService.selectAll();

        model.addAttribute("employee", employee);
        model.addAttribute("roleList", roleList);
        return "manage/employee/edit";
    }

    @PostMapping("/edit")
    public String editEmployee(Employee employee,Integer[] roleIds,RedirectAttributes redirectAttributes){
        employeeService.editEmployee(employee,roleIds);

        redirectAttributes.addFlashAttribute("massage", "更改成功");
        return "redirect:/manage/employee";
    }

    @GetMapping("/{id:\\d+}/frozen")
    @ResponseBody
    public ResponseBean frozen(@PathVariable Integer id){
        try{
            employeeService.frozenEmploee(id);
            return ResponseBean.success();
        } catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/leave")
    @ResponseBody
    public ResponseBean leave(@PathVariable Integer id){
        try{
            employeeService.leaveEmployee(id);
            return ResponseBean.success();
        } catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean del(@PathVariable Integer id){
        try{
            employeeService.delEmployee(id);
            return ResponseBean.success();
        } catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }




}
