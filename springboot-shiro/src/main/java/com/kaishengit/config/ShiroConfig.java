package com.kaishengit.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.kaishengit.entity.Permission;
import com.kaishengit.entity.PermissionExample;
import com.kaishengit.mapper.PermissionMapper;
import com.kaishengit.service.PermissionService;
import com.kaishengit.shiro.ShiroRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:55 2018/8/25
 */
@Configuration
@ControllerAdvice
public class ShiroConfig {

    @Autowired
    private PermissionService permissionService;

    /**
     * 登录异常处理
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationExceptionHandler(){
        return "403";
    }

    @Bean
    public Realm realm(){
        return new ShiroRealm();
    }

    /**
     * shiro + thymeleaf
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 配置初始权限过滤器关系（权限映射关系）  filterChainDefinition
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){

        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        Map<String,String> chianMap = permissionService.getChain();
        chainDefinition.addPathDefinitions(chianMap);
       /* for(Map.Entry<String,String> entry : chianMap.entrySet()){
            chainDefinition.addPathDefinition(entry.getKey(),entry.getValue());

        }*/

        chainDefinition.addPathDefinition("/**","authc");
        return chainDefinition;
    }

}
