package com.kaishengit.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chuzhaohui
 * @Date: Created in 16:46 2018/8/23
 */
@RestController
//RestController = Controller + ResponseBody
public class RedisController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/redisson/set")
    public String redissonSet(){
        RBucket<String> rBucket = redissonClient.getBucket("user2");
        if(rBucket.isExists()){
            return "该user已存在，" + rBucket.get();
        }else{
            rBucket.set("wangwu");
        }
        return "新增" + rBucket.get();
    }




    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /*@Autowired
    private void setRedisTemplate(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
    }*/

    @GetMapping("/sdr/set")
    public String springDataRedisSet(){
        redisTemplate.opsForValue().set("userName:2","jack");
        return "success";
    }
    @GetMapping("/sdr/get")
    public String springDataRedisGet(){
        String userName2 = redisTemplate.opsForValue().get("userName:2");
        String user = redisTemplate.opsForValue().get("userName");
        return user + " and "+ userName2;
    }
}
