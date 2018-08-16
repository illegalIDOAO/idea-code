package com.kaishengit.shiro;

import com.kaishengit.entity.Permission;
import com.kaishengit.service.PermissionService;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 在容器启动时加载一次，不能动态刷新权限
 *
 * @Author: chuzhaohui
 * @Date: Created in 18:00 2018/7/31
 */
public class MyFilterChainDefinitions implements FactoryBean<Ini.Section> {

    @Autowired
    private PermissionService permissionService;

    public String getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    private String filterChainDefinitions;


    @Override
    public Ini.Section getObject() throws Exception {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

        List<Permission> permissionList = permissionService.selectAll();
        for(Permission permission : permissionList){
            section.put(permission.getUrl(),"perms[" + permission.getPermissionCode() + "]");
        }

        section.put("/**","user");

        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return Ini.Section.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
