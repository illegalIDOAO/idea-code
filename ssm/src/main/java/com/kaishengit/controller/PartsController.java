package com.kaishengit.controller;

import com.kaishengit.entity.Parts;
import com.kaishengit.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:31 2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;

    @GetMapping("/detail")
    public String detail(int id, Model model){
        Parts parts = partsService.findParsById(id);
        model.addAttribute("parts",parts);
        return "parts/detail";
    }
}
