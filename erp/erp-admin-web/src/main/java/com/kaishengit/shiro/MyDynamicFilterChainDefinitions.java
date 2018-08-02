package com.kaishengit.shiro;

import com.kaishengit.entity.Permission;
import com.kaishengit.service.PermissionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:54 2018/8/1
 */
public class MyDynamicFilterChainDefinitions {

    @Autowired
    private PermissionService permissionService;

    private String filterChainDefinitions;
    private AbstractShiroFilter shiroFilter;

    @PostConstruct
    public synchronized void ini(){
        getFilterChainManager().getFilterChains().clear();
        load();
    }

    public synchronized void update(){
        getFilterChainManager().getFilterChains().clear();
        load();
    }

    private void load(){
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

        List<Permission> permissionList = permissionService.selectAll();
        for(Permission permission : permissionList) {
            section.put(permission.getUrl(), "perms[" + permission.getPermissionCode() + "]");
        }

        section.put("/**","user");

        DefaultFilterChainManager manager = getFilterChainManager();
        for(Map.Entry<String,String> entry : section.entrySet()){
            manager.createChain(entry.getKey(),entry.getValue());
        }
    }

    private DefaultFilterChainManager getFilterChainManager(){
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) this.shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
        return manager;
    }

    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
}
