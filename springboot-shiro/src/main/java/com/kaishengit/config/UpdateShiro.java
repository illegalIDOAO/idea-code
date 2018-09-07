package com.kaishengit.config;

import com.kaishengit.service.PermissionService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:54 2018/8/29
 */
@Component
public class UpdateShiro {

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private PermissionService permissionService;

    /**
     * 更新权限过滤器（权限映射关系）  通过filterChainManager
     * @return
     */
    public void update() throws Exception {

        AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

        manager.getFilterChains().clear();

        Map<String,String> chianMap = permissionService.getChain();
        for(Map.Entry<String,String> entry : chianMap.entrySet()){
            manager.createChain(entry.getKey(),entry.getValue());
        }
        manager.createChain("/**","authc");
    }

}
