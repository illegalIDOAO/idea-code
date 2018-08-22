package com.kaishengit.controller;

import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.Employee;
import com.kaishengit.entity.FixOrder;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.FixOrderService;
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
 * @Date: Created in 9:23 2018/8/7
 */
@Controller
@RequestMapping("/fix")
public class FixController {

    @Autowired
    private FixOrderService fixOrderService;

    @GetMapping("/list")
    public String fixList(Model model){
        List<FixOrder> fixOrderList = fixOrderService.selectFixOrderList();
        model.addAttribute("fixOrderList",fixOrderList);
        return "fix/list";
    }

    @GetMapping("/{id:\\d+}/pullTast")
    @ResponseBody
    public ResponseBean pullTast(@PathVariable int id){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try{
            fixOrderService.pullTast(id,employee.getId());
            return ResponseBean.success();
        } catch (ServiceException e){

            System.out.println("NotAllowException--------------");
            System.out.println(e.getMessage());
            System.out.println("-------------------------------");

            return ResponseBean.error(e.getMessage());
        }catch (Exception e){

            System.out.println("-------------------------------");
            System.out.println(e.getMessage());
            System.out.println("-------------------------------");

            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable int id,Model model){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        model.addAttribute("curr_employee_id",employee.getId());

        FixOrder fixOrder = fixOrderService.selectFixOrderDetail(id);
        model.addAttribute("fixOrder",fixOrder);
        return "fix/detil";
    }

    @GetMapping("/{id:\\d+}/done")
    public String done(@PathVariable int id, RedirectAttributes redirectAttributes){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try{
            fixOrderService.done(id,employee.getId());
            redirectAttributes.addFlashAttribute("message","提交成功");
        }catch(NotAllowException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/fix/list";
    }

}
