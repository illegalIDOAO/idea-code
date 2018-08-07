package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Employee;
import com.kaishengit.entity.Parts;
import com.kaishengit.entity.Type;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.controller.controllerExceptionHandeler.NotFountException;
import com.kaishengit.service.PartsService;
import com.kaishengit.service.TypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:31 2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "p",defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false) String partsName,
                       @RequestParam(required = false) String inventory,
                       @RequestParam(required = false) Integer partsType,
                       Model model){
        if(!StringUtils.isNumeric(inventory)){
            inventory = Integer.toString(10000);
        }else{
            inventory = Integer.toString(Integer.parseInt(inventory) + 1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("partsName", partsName);
        map.put("inventory", inventory);
        map.put("typeId", partsType);

        PageInfo<Parts> page = partsService.selectPage(pageNo, map);
        List<Type> typeList = typeService.findTypes();

        model.addAttribute("page", page);
        model.addAttribute("typeList", typeList);
        return "parts/list";
    }

    @GetMapping("/new")
    public String partsNew(Model model){
        List<Type> typeList = typeService.findTypes();
        model.addAttribute("typeList",typeList);
        return "parts/new";
    }

    @GetMapping("/checkPartsNo")
    @ResponseBody
    public String partsCheck(String partsNo){
        try{
            partsService.check(partsNo);
        }catch(NotAllowException e){
            return "false";
        }
        return "true";
    }

    @PostMapping("/new")
    public String partsNew(Parts parts,HttpSession session,RedirectAttributes redirectAttributes){
        Integer employeeId = ((Employee)session.getAttribute("currentEmployee")).getId();
        partsService.partsNew(parts,employeeId);
        redirectAttributes.addFlashAttribute("message","入库成功");
        return "redirect:/parts/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String partsDel(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        try{
            partsService.delect(id);
            redirectAttributes.addFlashAttribute("message","删除成功");
        } catch (NotAllowException e){
            redirectAttributes.addFlashAttribute("message","删除失败，该部件有库存");
        }
        return "redirect:/parts/list";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String partsEdit(@PathVariable Integer id,Model model){
        Parts parts = partsService.selectParsById(id);
        if(parts == null){
            throw new NotFountException("资源未找到");
        }
        List<Type> typeList = typeService.findTypes();
        model.addAttribute("parts",parts);
        model.addAttribute("typeList",typeList);
        return "parts/edit";
    }

    @PostMapping("/edit")
    public String partsEdit(Parts parts,RedirectAttributes redirectAttributes){
        partsService.partsEdit(parts);

        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/parts/list";
    }

    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable int id, Model model) throws IOException {
        Parts parts = partsService.selectParsById(id);
        if(parts == null){
            throw new NotFountException();
        }
        System.out.println(parts.getType());
        model.addAttribute("parts",parts);
        return "parts/detail";
    }

    @PostMapping("/addInventory")
    public String addInventory(String partsId, String addNum, HttpSession session , RedirectAttributes redirectAttributes){

        if(!StringUtils.isNumeric(addNum)){
            redirectAttributes.addFlashAttribute("message","增加备件成功,请输入有效数字");
            return "redirect:/parts/"+partsId+"/detail";
        }else{
            Integer employeeId = ((Employee)session.getAttribute("currentEmployee")).getId();
            partsService.addInventory(Integer.valueOf(partsId),Integer.valueOf(addNum),employeeId);
            redirectAttributes.addFlashAttribute("message","增加备件成功");
            return "redirect:/parts/"+partsId+"/detail";
        }
    }

    @GetMapping("/partsInList")
    public String partsOutList(){
        return "parts/partsInList";
    }
    @GetMapping("/partsOutList")
    public String partsInList(){
        return "parts/partsOutList";
    }

}
