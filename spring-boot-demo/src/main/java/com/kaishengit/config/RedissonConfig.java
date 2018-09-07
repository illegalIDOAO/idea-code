package com.kaishengit.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:09 2018/8/23
 */
@Configuration
public class RedissonConfig {

    /**
     * 两个以上的参数时不推荐使用@Value注解读取配置的方式获取参数，类内代码显得臃肿，推荐使用配置类的方式
     */
    /*@Value("${spring.redisson.address}")
    private String address;
    @Value("${spring.redisson.timeout}")
    private Integer timeout;*/

    @Autowired
    private RedissonConfigProperty property;
    
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress(property.getAddress()).setTimeout(property.getTimeout());
        return Redisson.create(config);
    }

}
