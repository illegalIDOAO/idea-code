package com.kaishengit.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:56 2018/8/25
 */
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roleSet = new HashSet<>();
        roleSet.add("admin");
        roleSet.add("manager");

        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("user:create");
        permissionSet.add("user:query");

        /*List<Permission> permissionList = new ArrayList<>();
        for(Role role : roleListOfUser()){
            roleCodeSet.add(role.getRoleCode());
            List<Permission> permissions = permissionService.selectPermissionByRoleId(role.getId());
            permissionList.addAll(permissions);
        }
        for(Permission permission : permissionList){
            permissionSet.add(permission.getPermissionCode());
        }*/
        //permissionSet.add("xxx");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roleSet);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);

        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        if("tom".equals(userName)){
            return new SimpleAuthenticationInfo(userName,"123",getName());
        }else {
            throw new UnknownAccountException("账号或密码错误");
        }
    }
}
