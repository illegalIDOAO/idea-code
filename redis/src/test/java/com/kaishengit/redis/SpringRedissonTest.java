package com.kaishengit.redis;

import com.kaishengit.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chuzhaohui
 * @Date: Created in 23:05 2018/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redisson.xml")
public class SpringRedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testGet(){
        RBucket<String> bucket = redissonClient.getBucket("userName");
        String string = bucket.get();
        System.out.println(string);

        RBucket<User> userBucket = redissonClient.getBucket("user:1003");
        User user = userBucket.get();
        System.out.println(user);
    }

    @Test
    public void testSet(){
        RBucket<String> bucket = redissonClient.getBucket("userName");
        bucket.set("tony");
    }
}
