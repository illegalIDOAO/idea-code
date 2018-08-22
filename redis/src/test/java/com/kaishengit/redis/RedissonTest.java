package com.kaishengit.redis;

import com.kaishengit.User;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:43 2018/8/21
 */
public class RedissonTest {

    @Test
    public void testGet(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.10:6379");

        RedissonClient redissonClient = Redisson.create(config);
        RBucket<String> rBucket = redissonClient.getBucket("user:1002");
        System.out.println(rBucket.get());

        RBucket<User> userRBucket = redissonClient.getBucket("user:1003");
        System.out.println(userRBucket.get());

    }

    @Test
    public void testSet(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.10:6379");

        RedissonClient redissonClient = Redisson.create(config);
        RBucket<String> rBucket = redissonClient.getBucket("user:1002");
        rBucket.set("rouse");

        RBucket<User> userRBucket = redissonClient.getBucket("user:1003");

        User user = new User();
        user.setId("1003");
        user.setName("joure");
        user.setAge("28");
        userRBucket.set(user);
    }


    @Test
    public void testLock(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.10:6379");
        RedissonClient redissonClient = Redisson.create(config);

        RLock rLock = redissonClient.getLock("product:1001");

        boolean flag = false;
        while(!flag){
            flag = rLock.tryLock();
            if(flag){
                System.out.println("等待。。。");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try{
            //...
            Thread.sleep(10000);
            System.out.println("end...");

        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
    }

}
