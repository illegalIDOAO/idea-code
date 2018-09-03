package com.kaishengit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:44 2018/9/3
 */
@RestController
public class XController {

    @GetMapping("/get")
    public String GetName(){

        return "hello";
    }
}
