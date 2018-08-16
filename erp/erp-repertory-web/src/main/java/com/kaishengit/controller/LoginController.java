package com.kaishengit.controller;

import com.kaishengit.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: chuzhaohui
 * @Date: Created in 16:53 2018/7/30
 */
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/")
    public String login(){
        Subject subject = SecurityUtils.getSubject();

        //跳转登录页则取消登录状态
        if(subject.isAuthenticated()){
            subject.logout();
        }

        //勾选rememberme免登录，若是跳转登录页则跳过
        if(subject.isRemembered()){
            return "home";
        }
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/")
    public String login(String employeeAccount,
                        String password,
                        String remember,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes){

        //创建subject主体
        Subject subject = SecurityUtils.getSubject();
        //获得登录loginIp
        String loginIp = request.getRemoteAddr();

        //通过emplyAccount、password封装UsernamePasswordToken对象进行登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(employeeAccount,
                DigestUtils.md5Hex(password + Constant.ADMIN_PASSWORD_SALT), remember != null, loginIp);

        try{
            subject.login(usernamePasswordToken);

            if(subject.hasRole("repertoryManage")){
                //回调callback
                String url = "/home";
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                if(savedRequest != null){
                    url = savedRequest.getRequestURI();
                }
                return "redirect:" + url;
            }else{
                redirectAttributes.addFlashAttribute("message","权限不足");
            }
        } catch (UnknownAccountException | IncorrectCredentialsException e){
            redirectAttributes.addFlashAttribute("message","账号或用户名错误");
        } catch (LockedAccountException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        } catch (AuthenticationException e){
            redirectAttributes.addFlashAttribute("message","登录失败");
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        redirectAttributes.addFlashAttribute("message","退出登录");
        return "redirect:/";
    }

    @GetMapping("/401")
    public String unauthorizedUrl(){
        return "error/401";
    }

}
