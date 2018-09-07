package com.kaishengit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:18 2018/8/23
 */
@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonConfigProperty {

    private String address;
    private Integer timeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
