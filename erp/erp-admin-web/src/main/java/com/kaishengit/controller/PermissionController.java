package com.kaishengit.controller;

import com.google.common.collect.Lists;
import com.kaishengit.controller.controllerExceptionHandeler.NotFountException;
import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.Permission;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.PermissionService;
import com.kaishengit.shiro.MyDynamicFilterChainDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:50 2018/7/27
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private MyDynamicFilterChainDefinitions myDynamicFilterChainDefinitions;
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String home(Model model){
        List<Permission> permissionList = permissionService.selectAll();
        model.addAttribute("permissionList",permissionList);
        return "manage/permission/home";
    }

    @GetMapping("/new")
    public String newPermission(Model model){
        List<Permission> menuPermissionList = permissionService.selectMenuPermission();

        model.addAttribute("menuPermissionList",menuPermissionList);
        return "manage/permission/new";
    }

    @PostMapping("/new")
    public String newPermission(Permission permission, RedirectAttributes redirectAttributes){
        permissionService.newPermission(permission);

        myDynamicFilterChainDefinitions.update();

        redirectAttributes.addFlashAttribute("message","添加权限成功");
        return "redirect:/manage/permission";
        //TODO 增加是否新增成功判断
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean del(@PathVariable int id){
        try{
            permissionService.del(id);

            myDynamicFilterChainDefinitions.update();

            return ResponseBean.success();
        }catch (NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable int id,Model model){
        Permission permission = permissionService.selectPermissionById(id);
        if(permission == null){
            throw new NotFountException("资源未找到");
        }
        model.addAttribute("permission",permission);

        List<Permission> menuPermissionList = permissionService.selectMenuPermission();
        /*menuPermissionList.remove(permission);
        extend(menuPermissionList);*/
        remove(menuPermissionList,permission);

        model.addAttribute("menuPermissionList",menuPermissionList);
        return "manage/permission/edit";
    }
    //在集合中去掉子权限
    private void extend( List<Permission> sourceList){
        List<Permission> tempList = new ArrayList<>();
        for(Permission permission1 : sourceList){
            //flagIn=true表示需要移除
            boolean flag = true;
            if(permission1.getPid().equals(0)){
                flag = false;
            }else{
                for(Permission permission2 : sourceList){
                    System.out.println(permission1);
                    if(permission1.getPid().equals(permission2.getId())){
                        flag = false;
                        System.out.println(permission2);
                        break;
                    }
                }
            }
            if(flag){
                tempList.add(permission1);
            }
        }
        if(tempList.size() != 0){
            for(Permission permission : tempList){
                sourceList.remove(permission);
            }
            extend(sourceList);
        }
    }
    private void remove(List<Permission> sourceList,Permission permission){
        List<Permission> tempList = Lists.newArrayList(sourceList);
        for(int i = 0 ; i< tempList.size() ;i++){
            if(tempList.get(i).getPid().equals(permission.getId())){
                remove(sourceList,tempList.get(i));
            }
        }
        sourceList.remove(permission);
    }

    @PostMapping("/edit")
    public String edit(Permission permission, RedirectAttributes redirectAttributes){
        permissionService.editPermission(permission);

        myDynamicFilterChainDefinitions.update();

        redirectAttributes.addFlashAttribute("message","修改权限成功");
        return "redirect:/manage/permission";
    }


}
