package com.kaishengit.controller;

import com.kaishengit.entity.Movie;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:44 2018/9/3
 */
@RestController
public class XController {

    @GetMapping("/get")
    public String GetName(){
        System.out.println("hello");
        return "hello";
    }

    @GetMapping("/movie/{id:\\d+}")
    public String getMovieName(@PathVariable Integer id) {
        System.out.println("id:-------->" + id);
        return "大话西游2";
    }

    @PostMapping("/movie/new")
    public String newMovie(String movieName, String year) {
        System.out.println("movieName: " + movieName);
        System.out.println("year:" + year);
        return "save success!";
    }

    @PostMapping("/movie/save")
    public String saveMovie(@RequestBody Movie movie) {
        System.out.println("movieName: " + movie.getMovieName());
        System.out.println("year:" + movie.getYear());
        return "save success!";
    }
}
