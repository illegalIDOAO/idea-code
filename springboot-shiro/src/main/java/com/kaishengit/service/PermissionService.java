package com.kaishengit.service;

import com.kaishengit.entity.Permission;
import com.kaishengit.entity.PermissionExample;
import com.kaishengit.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:58 2018/8/29
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 获得权限映射关系
     * @return
     */
    public Map<String,String> getChain(){
        Map<String,String> chianMap = new HashMap<>();
        chianMap.put("/favicon.ico","anon");
        chianMap.put("/css/**","anon");
        chianMap.put("/js/**","anon");
        chianMap.put("/fonts/**","anon");
        chianMap.put("/logout","logout");

        PermissionExample permissionExample = new PermissionExample();
        List<Permission> permissionList =  permissionMapper.selectByExample(permissionExample);
        for(Permission permission : permissionList) {
            chianMap.put(permission.getUrl(), "perms[" + permission.getPermissionCode() + "]");
        }
        //section.put("/**","authc");
        return chianMap;
    }
}
