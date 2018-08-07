package com.kaishengit.controller;

import com.kaishengit.controller.controllerExceptionHandeler.NotFountException;
import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.Permission;
import com.kaishengit.entity.Role;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.PermissionService;
import com.kaishengit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:49 2018/7/27
 */
@Controller
@RequestMapping("/manage/role")
public class RoleContorller {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String home(Model model){
        List<Role> roleList = roleService.selectAll();
        model.addAttribute("roleList",roleList);
        return "manage/role/home";
    }

    @GetMapping("/new")
    public String newRole(Model model){
        List<Permission> permissionList = permissionService.selectAll();
        model.addAttribute("permissionList",permissionList);
        return "manage/role/new";
    }

    @PostMapping("/new")
    public String newRole(Role role,Integer[] permissionIds, RedirectAttributes redirectAttributes){

        roleService.newRole(role,permissionIds);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/manage/role";
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean del(@PathVariable int id){
        try{
            roleService.delRole(id);
            return ResponseBean.success();
        }catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable int id,Model model){
        Role role = roleService.selectById(id);
        if(role == null){
            throw new NotFountException("资源未找到");
        }

        List<Permission> rolePermissionList = role.getPermissionList();

        List<Permission> permissionList = permissionService.selectAll();
        for(Permission rolePermisson : rolePermissionList){
            for(Permission permission : permissionList){
                if(permission.getId().equals(rolePermisson.getId())){
                    permission.setChecked(Permission.PERMISSION_CHECKED);
                    break;
                }
            }
        }

        model.addAttribute("role",role);
        model.addAttribute("permissionList",permissionList);

        return "manage/role/edit";
    }

    @PostMapping("/edit")
    public String edit(Role role,Integer[] permissionId ,RedirectAttributes redirectAttributes){
        roleService.editRole(role,permissionId);

        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/manage/role";
    }

}
