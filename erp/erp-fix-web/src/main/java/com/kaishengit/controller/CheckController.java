package com.kaishengit.controller;

import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.Employee;
import com.kaishengit.entity.FixOrder;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.CheckOrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:44 2018/8/10
 */
@Controller
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckOrderService checkOrderService;

    @GetMapping("/list")
    public String checkList(Model model){
        List<FixOrder> fixOrderList = checkOrderService.selectCheckList();
        model.addAttribute("fixOrderList",fixOrderList);
        return "check/list";
    }

    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable int id, Model model){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        model.addAttribute("curr_employee_id",employee.getId());

        FixOrder fixOrder = checkOrderService.selectChekcOrderDetail(id);
        model.addAttribute("fixOrder",fixOrder);
        return "check/detil";
    }

    @GetMapping("/{id:\\d+}/pullTast")
    @ResponseBody
    public ResponseBean pullTast(@PathVariable int id){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try{
            checkOrderService.pullTast(id,employee.getId());
            return ResponseBean.success();
        } catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/done")
    public String done(@PathVariable int id, RedirectAttributes redirectAttributes){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try{
            checkOrderService.done(id,employee.getId());
            redirectAttributes.addFlashAttribute("message","提交成功");
        }catch(NotAllowException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/check/list";
    }

}
