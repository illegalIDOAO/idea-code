package com.kaishengit.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Parts;
import com.kaishengit.entity.Type;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.exception.NotFountException;
import com.kaishengit.service.PartsService;
import com.kaishengit.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                       @RequestParam(required = false) Integer partsType,
                       Model model){

        Map<String,Object> map = new HashMap<>();
        map.put("partsName",partsName);
        map.put("typeId",partsType);

        PageInfo<Parts> page = partsService.findPage(pageNo,map);
        List<Type> typeList = typeService.findTypes();

        model.addAttribute("page",page);
        model.addAttribute("typeList",typeList);
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
    public String partsNew(Parts parts,RedirectAttributes redirectAttributes){
        partsService.partsNew(parts);
        redirectAttributes.addFlashAttribute("message","入库成功");
        return "redirect:/parts/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String partsDel(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        partsService.delect(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/parts/list";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String partsEdit(@PathVariable Integer id,Model model){
        Parts parts = partsService.findParsById(id);
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


    @GetMapping("/detail")
    public String detail(int id, Model model) throws IOException {
        Parts parts = partsService.findParsById(id);

        if(parts == null){
            throw new NotFountException();
        }
        model.addAttribute("parts",parts);
        return "parts/detail";
    }
}
