package com.kaishengit.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:47 2018/9/3
 */
@Configuration
public class CloudConfig {

    @Bean
    @LoadBalanced//使用Robbin时使用可使用此注解
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
