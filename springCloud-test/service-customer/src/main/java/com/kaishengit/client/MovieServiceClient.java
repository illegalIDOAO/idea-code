package com.kaishengit.client;

import com.kaishengit.entity.Movie;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:27 2018/9/3
 */
@FeignClient("EUREKA-SERVER-PROVIDER")
public interface MovieServiceClient {

    // 必须使用@PathVariable(name = "id") 实现param和url中id的绑定
    @GetMapping("/movie/{id}")
    String getMovie(@PathVariable(name="id") int id);

    @PostMapping("/movie/new")
    String newMovie(@RequestParam(name = "movieName") String movieName,
                    @RequestParam(name = "year") String year);

    @PostMapping("/movie/save")
    String saveMovie(Movie movie);

}
