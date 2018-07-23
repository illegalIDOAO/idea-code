package com.kaishengit.controller;

import com.kaishengit.entity.Student;
import com.kaishengit.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:51 2018/7/20
 */
@Controller
@RequestMapping("/user")
//ResponseBody
public class UserController {

    @GetMapping("/add")
    public String addUser(){
        return "user/add";
    }

    @GetMapping("/add/{id:\\d+}")
    public String addUser(@PathVariable int id){
        System.out.println(id);
        return "user/add";
    }

    @GetMapping("/new/{id:.+}")
    public String newUser(@RequestParam(name = "pageNo",defaultValue = "1") int p, @PathVariable(name = "id") int d){
        System.out.println("id : " + d);
        System.out.println("p : " + p);
        return "user/add";
    }

    @GetMapping("/add/{type:v-\\d+}/{id:\\d+}")
    public String addUser(@PathVariable int id,@PathVariable String type,Model model){
        System.out.println(id);
        System.out.println(type);
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        return "user/add";
    }

    //@PostMapping("/add")
    public String addUser(String userName,String password,String tel,Model model){
        System.out.println(userName);
        System.out.println(password);
        System.out.println(tel);

        model.addAttribute("userName",userName);
        model.addAttribute("password",password);
        model.addAttribute("tel",tel);

        return "user/home";
    }

    @PostMapping("/add")
    public String addUser1(String userName,String password,String tel,Model model){
        System.out.println(userName);
        System.out.println(password);
        System.out.println(tel);

        model.addAttribute("userName",userName);
        model.addAttribute("password",password);
        model.addAttribute("tel",tel);

        Student student= new Student("tony","25");
        System.out.println(student);
        model.addAttribute("student",student);


        return "redirect:/user/home";
    }

    @GetMapping("/home")
    public String homeUserGet(String userName,String password,String tel,Student student,Model model){
        System.out.println("userName :" + userName);
        System.out.println("password :" + password);
        System.out.println("tel :" + tel);
        System.out.println("Studnet : " + student);

        model.addAttribute("userName",userName);
        model.addAttribute("password",password);
        model.addAttribute("tel",tel);

        return "user/home";
    }
   /* @GetMapping("/home")
    public ModelAndView homeUserGet(String userName,String password,String tel){
        System.out.println("userName :" + userName);
        System.out.println("password :" + password);
        System.out.println("tel :" + tel);

        ModelAndView modelAndView = new ModelAndView("user/home");
        modelAndView.addObject("userName",userName);
        modelAndView.addObject("password",password);
        modelAndView.addObject("tel",tel);

        return modelAndView;
    }*/

    @PostMapping("/home")
    public ModelAndView homeUser(String userName,String password,String tel){
        System.out.println(userName);
        System.out.println(password);
        System.out.println(tel);

        ModelAndView modelAndView = new ModelAndView("user/home");
        modelAndView.addObject("userName",userName);
        modelAndView.addObject("password",password);
        modelAndView.addObject("tel",tel);

        return modelAndView;
    }

/*注解@ResponseBody，声明响应内容，不会通过视图解析器
 *若是String类型则作为响应输入
 *若是Json,则需要添加maven，然后springMVC会自动匹配
 * 该注释可加在类上，相当于给本类下所有方法都加注释
 */

    @GetMapping(value="/passString",produces = "text/plain;charset=UTF-8")
    //@GetMapping("/passString")
    @ResponseBody
    public String passString(){
        System.out.println("passString");
        String word = "你好";
        return word;
    }

    @GetMapping("/passJson")
    @ResponseBody
    public List<User> passJson(){
        List<User> userList = Arrays.asList(
                new User("tom","123"),
                new User("张三","123")
        );
        return userList;
    }


    @GetMapping("/http")
    public String http(HttpSession session,HttpServletRequest request, HttpServletResponse response,
                       @RequestHeader(name = "user-Agent") String userAgent){

        System.out.println(userAgent);

        //通过request获得session和context
        HttpSession session1 = request.getSession();
        ServletContext context = request.getServletContext();

        User user = new User("tom","123");
        session.setAttribute("user",user);
        User user1 = (User) session.getAttribute("user");
        System.out.println(user1);

        Cookie cookie = new Cookie("name",user1.getUsername());
        cookie.setDomain("localhost");
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return "hello";
    }

    @GetMapping("/hello")
    public String getCookie(@CookieValue String name, @RequestHeader(name = "user-Agent") String userAgent){

        System.out.println(name);

        System.out.println(userAgent);
        return "hello";
    }

}
