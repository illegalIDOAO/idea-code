package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.RequestWrapper;

/**
 * @Author: chuzhaohui
 * @Date: Created in 18:43 2018/7/19
 */
@Controller
public class helloController {

    @RequestMapping(value = "/hello", method = {RequestMethod.GET,RequestMethod.POST})
    //@PostMapping("/hello")
    //@GetMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "hello";
    }
}
