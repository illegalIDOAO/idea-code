package com.kaishengit.controller;

import com.kaishengit.client.MovieServiceClient;
import com.kaishengit.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:46 2018/9/3
 */
@RestController
public class XController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieServiceClient movieServiceClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/get1")
    public String getMessage1() {

        String url = "http://localhost:8001/get";
        String forObject = restTemplate.getForObject(url, String.class);
        return forObject;
    }

    @GetMapping("/get2")
    public String getMessage2(){
        ServiceInstance instance = loadBalancerClient.choose("EUREKA-SERVER-PROVIDER");
        System.out.println(instance.getUri());
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        System.out.println(instance.getMetadata());
        System.out.println(instance.getServiceId());

        String url = instance.getUri() + "/get";
        String forObject = restTemplate.getForObject(url, String.class);
        return forObject;
    }

    @GetMapping("/movie/{id:\\d+}")
    public String getMovie1(@PathVariable int id){
        String url = "http://EUREKA-SERVER-PROVIDER/movie/{id}";
        return restTemplate.getForObject(url, String.class, id);
    }

    @GetMapping("/movie2/{id:\\d+}")
    public String getMovie2(@PathVariable int id){
        return movieServiceClient.getMovie(id);
    }


    @PostMapping("/movie/new")
    public String newMovie(){
        return movieServiceClient.newMovie("速度与激情8","2016");
    }

    @PostMapping("/movie/save")
    public String saveMovie(){
        Movie movie = new Movie();
        movie.setMovieName("阿凡达");
        movie.setYear("2008");
        return movieServiceClient.saveMovie(movie);
    }


}
