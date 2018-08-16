package com.kaishengit.controller;

import com.kaishengit.entity.Type;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:54 2018/7/25
 */
@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private PartsService partsService;

    @GetMapping("/list")
    public String typeList(Model model){
        List<Type> typeList = partsService.findTypes();
        model.addAttribute("typeList",typeList);
        return "parts/type";
    }

    @GetMapping("/{id:\\d+}/del")
    public String typeDel(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            partsService.typeDel(id);
            redirectAttributes.addFlashAttribute("message","删除成功");
        } catch (NotAllowException e){
            redirectAttributes.addFlashAttribute("message","该类型下有零部件");
        }
        return "redirect:/type/list";
    }

    @PostMapping("/new")
    public String typeNew(String typeName, RedirectAttributes redirectAttributes){

        try{
            partsService.typeNew(typeName);
            redirectAttributes.addFlashAttribute("message","新增类型成功");
        }catch (NotAllowException e){
            redirectAttributes.addFlashAttribute("message","新增类型失败，该类型已存在");
        }
        return "redirect:/type/list";
    }

}
