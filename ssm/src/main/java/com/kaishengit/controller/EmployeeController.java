package com.kaishengit.controller;

import com.kaishengit.entity.Employee;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:44 2018/7/25
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/login")
    public String login(){
        return "employee/login";
    }

    @PostMapping("/login")
    public String login(Employee employee, HttpSession session, RedirectAttributes redirectAttributes){
        try{
            Employee currentEmployee = employeeService.login(employee);
            session.setAttribute("currentEmployee",currentEmployee);
            return "redirect:/parts/list";
        }catch (NotAllowException e){
            redirectAttributes.addAttribute("message",e.getMessage());
            return "redirect:/employee/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("currentEmployee");
        return "redirect:/employee/login";
    }

    @GetMapping("/{id:\\d+}/profile")
    public String profile(@PathVariable int id, Model model){
        Employee employee = employeeService.selectEmployeById(id);
        model.addAttribute("employee",employee);
        return "employee/profile";
    }


    @GetMapping("/list")
    public String employeeList(Model model){
        List<Employee> employeeList = employeeService.selectEmployeeList();
        model.addAttribute("employeeList", employeeList);
        return "employee/list";
    }

}
