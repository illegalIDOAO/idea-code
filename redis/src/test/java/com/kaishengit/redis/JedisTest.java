package com.kaishengit.redis;

import com.alibaba.fastjson.JSON;
import com.kaishengit.User;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:30 2018/8/21
 */
public class JedisTest {

    @Test
    public void testSetString(){
        Jedis jedis = new Jedis("192.168.1.10",6379);
        jedis.set("name","tom");
        jedis.close();
    }
    @Test
    public void testGetString(){
        Jedis jedis = new Jedis("192.168.1.10",6379);
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }
    @Test
    public void testDelString(){
        Jedis jedis = new Jedis("192.168.1.10",6379);
        jedis.del("name");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }
    @Test
    public void testSetObject(){
        User user = new User();
        user.setAge("20");
        user.setId("1001");
        user.setName("jojo");
        String stringUser = JSON.toJSONString(user);
        Jedis jedis = new Jedis("192.168.1.10",6379);
        jedis.set("user:1001",stringUser);
        jedis.close();
    }
    @Test
    public void testGetObject(){

        Jedis jedis = new Jedis("192.168.1.10",6379);
        String stringUser = jedis.get("user:1001");
        System.out.println(stringUser);
        User user = JSON.parseObject(stringUser,User.class);
        System.out.println(user);
        jedis.close();
    }

    @Test
    public void testPool(){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(5);
        JedisPool jedisPool = new JedisPool(config,"192.168.1.10",6379);

        Jedis jedis = jedisPool.getResource();
        String name = jedis.get("user:1001");
        System.out.println(name);
        User user = JSON.parseObject(name,User.class);
        System.out.println(user);
        jedis.close();
        jedisPool.destroy();
    }





}
