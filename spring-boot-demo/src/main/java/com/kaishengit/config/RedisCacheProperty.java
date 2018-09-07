package com.kaishengit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 13:11 2018/8/24
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisCacheProperty {

    public Map<String, Long> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }

    private Map<String, Long> expires;


}
