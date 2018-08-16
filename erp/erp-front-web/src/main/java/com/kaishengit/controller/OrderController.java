package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.kaishengit.controller.controllerExceptionHandeler.NotFountException;
import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.OrderService;
import com.kaishengit.service.PartsService;
import com.kaishengit.vo.OrderInfoVo;
import com.kaishengit.vo.OrderVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * @Date: Created in 14:32 2018/8/2
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PartsService partsService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/new")
    public String newOrder(){
        return "order/new";
    }

    @GetMapping("/service/getTypes")
    @ResponseBody
    public ResponseBean getServiceTypeList(){
        List<ServiceType> serviceTypeList = orderService.selectServiceTypeList();
        return ResponseBean.success(serviceTypeList);
    }

    @GetMapping("/parts/types")
    @ResponseBody
    public ResponseBean getPartsTypeList(){
        List<Type> typeList = partsService.findTypes();
        return ResponseBean.success(typeList);
    }

    @GetMapping("/{id:\\d+}/parts")
    @ResponseBody
    public ResponseBean getPartsTypeList(@PathVariable int id){
        List<Parts> partsList = partsService.findPartsByTypeId(id);
        return ResponseBean.success(partsList);
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseBean newOrder(String json){
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);

        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try {
            Integer orderId = orderService.newOrder(orderVo, employee.getId());
            return ResponseBean.success(orderId);
        }catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }catch(RuntimeException e){
            return ResponseBean.error("系统异常，下单失败");
        }

    }

    @GetMapping("/done/list")
    public String doneList(@RequestParam(name = "p",defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false) String carLiNo,
                       @RequestParam(required = false) String ownerName,
                       @RequestParam(required = false) String startTime,
                       @RequestParam(required = false) String endTime,
                       Model model){

        Map<String,String>map = new HashMap<>();
        map.put("carLiNo",carLiNo);
        map.put("ownerName",ownerName);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("state",Order.ORDER_STATE_DONE);

        PageInfo<Order> orderPageInfo = orderService.selectPage(pageNo, map);

        model.addAttribute("page",orderPageInfo);
        model.addAttribute("type","done");

        return "order/list";
    }

    @GetMapping("/undone/list")
    public String undoneList(@RequestParam(name = "p",defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false) String carLiNo,
                       @RequestParam(required = false) String ownerName,
                       @RequestParam(required = false) String startTime,
                       @RequestParam(required = false) String endTime,
                       Model model){

        Map<String,String>map = new HashMap<>();
        map.put("carLiNo",carLiNo);
        map.put("ownerName",ownerName);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("exState",Order.ORDER_STATE_DONE);

        PageInfo<Order> orderPageInfo = orderService.selectPage(pageNo, map);

        model.addAttribute("page",orderPageInfo);
        model.addAttribute("type","");
        return "order/list";
    }


    @GetMapping("{id:\\d++}/detail")
    public String detail(@PathVariable int id ,Model model){
        try{
            Order order = orderService.selectOrderWithCarAndCustomerById(id);
            ServiceType serviceType = orderService.selectServiceById(order.getServiceTypeId());
            List<Parts> partsList = orderService.selectPartsListByOrderId(id);

            model.addAttribute("order",order);
            model.addAttribute("serviceType",serviceType);
            model.addAttribute("partsList",partsList);
        }catch(NotAllowException e){
            throw new NotFountException(e.getMessage());
        }
        return "order/detail";
    }

    @GetMapping("/{id:\\d+}/del")
    public String delOrder(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            orderService.delOrder(id);
        }catch (NotAllowException e){
            redirectAttributes.addFlashAttribute("warning",e.getMessage());
        }
        return "redirect:/order/undone/list";
    }

    @GetMapping("/{id:\\d+}/trans")
    @ResponseBody
    public ResponseBean transOrder(@PathVariable int id){
        try{
            orderService.transOrder(id);
            return ResponseBean.success();
        }catch(NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable int id,Model model,RedirectAttributes redirectAttributes){
        try{
            orderService.selectOrderWithCarAndCustomerById(id);
        }catch(NotAllowException e){
            redirectAttributes.addFlashAttribute("warning",e.getMessage());
            return "redirect:/order/undone/list";
        }
        model.addAttribute("orderId",id);
        return "order/edit";
    }

    @GetMapping("/{id:\\d+}/orderInfo")
    @ResponseBody
    public ResponseBean orderInfo(@PathVariable int id){
        Order order = orderService.selectOrderWithCarAndCustomerById(id);
        ServiceType serviceType = orderService.selectServiceById(order.getServiceTypeId());
        List<Parts> partsList = orderService.selectPartsListByOrderId(id);

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrder(order);
        orderInfoVo.setServiceType(serviceType);
        orderInfoVo.setPartsList(partsList);
        return ResponseBean.success(orderInfoVo);
    }

    @PostMapping("/{id:\\d+}/edit")
    @ResponseBody
    public ResponseBean editOrder(@PathVariable int id ,String json){
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json,OrderVo.class);

        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try{
            orderService.editOrder(id,orderVo,employee.getId());
            return ResponseBean.success();
        }catch(NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }



}
