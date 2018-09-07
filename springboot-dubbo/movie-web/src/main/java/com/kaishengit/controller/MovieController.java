package com.kaishengit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kaishengit.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:06 2018/8/24
 */
@RestController
public class MovieController {

    @Reference(version = "1.0")
    private MovieService movieService;

    @GetMapping("/movie/{id:\\d+}")
    public String fingById(@PathVariable int id){
        System.out.println("hello");
        return movieService.findById(id);
    }
}
