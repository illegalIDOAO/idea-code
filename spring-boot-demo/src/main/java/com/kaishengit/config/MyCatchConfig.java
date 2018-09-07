package com.kaishengit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:05 2018/8/24
 */
@Configuration
@EnableCaching
public class MyCatchConfig {

    /**
     * 可以自动注入，也可以在方法的参数中直接注入
     */
    @Autowired
    private RedisCacheProperty redisCacheProperty;

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setUsePrefix(true);
        redisCacheManager.setExpires(redisCacheProperty.getExpires());
        return redisCacheManager;
    }

   /* @Bean
    public CacheManager cacheConfig() {
        return new EhCacheCacheManager();
    }*/
}
