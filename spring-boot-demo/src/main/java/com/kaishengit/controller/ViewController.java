package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:04 2018/8/22
 */
@Controller
public class ViewController {



    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("name","tom");
        model.addAttribute("id",1001);
        model.addAttribute("time",System.currentTimeMillis());
        model.addAttribute("num",12323532624.1234145);
        model.addAttribute("num1",0.1234145);
        List<String> toolList = Arrays.asList("pen","page","pc");
        model.addAttribute("toolList",toolList);
        return "home";
    }

    @GetMapping("/user/index")
    public String index1(@RequestParam int id,Model model){
        model.addAttribute("id",id);
        model.addAttribute("role","user");
        model.addAttribute("name","jack");

        return "index";
    }

    @GetMapping("/user/{id:\\d+}/index")
    public String index2(@PathVariable int id, Model model){
        model.addAttribute("id",id);
        model.addAttribute("role","admin");
        model.addAttribute("name","jack");
        Map<String,String> map = new HashMap();
        map.put("k1","v1");
        map.put("k2","v2");
        map.put("k3","v3");
        model.addAttribute("map",map);
        return "index";
    }

    @GetMapping("/tryPublic")
    public String tryPublic(){
        return "tryPublic";
    }

    @GetMapping("/news")
    public String news(){
        return "news";
    }

}
