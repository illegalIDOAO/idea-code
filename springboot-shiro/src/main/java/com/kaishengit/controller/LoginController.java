package com.kaishengit.controller;

import com.kaishengit.config.UpdateShiro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:54 2018/8/25
 */
@Controller
//@CrossOrigin("*")跨界访问注解
public class LoginController {

    @GetMapping("/")
    public String login(){
        return "index";
    }
    @PostMapping("/")
    public String postLogin(String userName, String password){

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);

        try{
            subject.login(token);
            return "redirect:/home";
        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/re1")
    @ResponseBody
    public String t(HttpServletRequest request){
        StringBuilder result = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();

        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String value = request.getHeader(headerName);
            result.append(headerName + " -> " + value).append("<br/>");
        }

        result.append("-------------------------------------<br/>");
        String ip = request.getRemoteAddr();
        result.append( "ip -> " + ip).append("<br/>");

        String sessionId = request.getSession().getId();
        result.append( "sessionId -> " + sessionId).append("<br/>");
        result.append("-------------------------------------<br/>");
        return result.toString();
    }

    @GetMapping("/re2")
    public String t2(HttpServletRequest request){

        return "redirect:/re1";
    }



    @Autowired
    private UpdateShiro updateConfig;

    @GetMapping("/update")
    @ResponseBody
    public String refreshPremession(){
        try {
            updateConfig.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "seccess";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/xxx")
    @ResponseBody
    public String xxx(){
        return "xxx";
    }

    @GetMapping("/yyy")
    @ResponseBody
    public String yyy(){
        return "yyy";
    }

}
