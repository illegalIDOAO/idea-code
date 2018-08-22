package com.kaishengit.redis;

import com.kaishengit.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:15 2018/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-data-jedis.xml")
public class SpringDataJedisTest {

    private RedisTemplate<String,User> redisTemplate;

    @Autowired
    private void setRedisTemplate(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
    }

    @Test
    public void testSet(){
        User user = new User();
        user.setId("1");
        user.setName("seer");
        user.setAge("28");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user:1",user);
    }

    @Test
    public void  testGet(){
        User user = redisTemplate.opsForValue().get("user: 1");
        System.out.println(user);
    }

}
