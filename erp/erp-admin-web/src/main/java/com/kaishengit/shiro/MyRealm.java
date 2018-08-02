package com.kaishengit.shiro;

import com.kaishengit.entity.Employee;
import com.kaishengit.entity.EmployeeLoginLog;
import com.kaishengit.entity.Permission;
import com.kaishengit.entity.Role;
import com.kaishengit.service.EmployeeService;
import com.kaishengit.service.PermissionService;
import com.kaishengit.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:27 2018/7/30
 */
public class MyRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 判断权限角色，授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();

        Set<String> roleCodeSet = new HashSet<>();
        Set<String> permissionCodeSet = new HashSet<>();

        List<Permission> permissionList = new ArrayList<>();
        for(Role role : employee.getRoleList()){
            roleCodeSet.add(role.getRoleCode());

            List<Permission> permissions = permissionService.selectPermissionByRoleId(role.getId());
            permissionList.addAll(permissions);
        }
        for(Permission permission : permissionList){
            permissionCodeSet.add(permission.getPermissionCode());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roleCodeSet);
        simpleAuthorizationInfo.setStringPermissions(permissionCodeSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 判断登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String account = usernamePasswordToken.getUsername();
        String loginIp = usernamePasswordToken.getHost();

        Employee employee = null;
        if(account != null){
            employee = employeeService.selectEmployeByAccount(account);
        }
        if(employee == null){
            throw new UnknownAccountException();
        } else if(!employee.getState().equals(Employee.EMPLOYEE_STATE_NORMAL)){
            throw new LockedAccountException("账户状态异常");
        } else {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(employee,employee.getPassword(),getName());

            logger.debug("{}{}在{}登录",employee.getEmployeeName(),employee.getEmployeeAccount(),new Date());

            EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
            employeeLoginLog.setEmployeeId(employee.getId());
            employeeLoginLog.setLoginIp(loginIp);
            employeeLoginLog.setLoginTime(new Date());
            employeeService.saveLogginLog(employeeLoginLog);

            return  authenticationInfo;
        }
    }
}
