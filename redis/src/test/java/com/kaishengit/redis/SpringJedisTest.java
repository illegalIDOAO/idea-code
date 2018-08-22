package com.kaishengit.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:52 2018/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jedis.xml")
public class SpringJedisTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testGet(){
        Jedis jedis = jedisPool.getResource();
        String name = jedis.get("user:1001");
        System.out.println(name);
        jedis.close();
    }

    @Test
    public void testSet(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("order","123");
        jedis.close();
    }



}
