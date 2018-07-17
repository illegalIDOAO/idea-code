package com.kaishengit.service;

import com.kaishengit.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:52 2018/7/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class UserServiceTestCase {
//public class UserServiceTestCase extends BaseService{
//也可使用继承父类的形式，使得代码更加简洁

    @Autowired
    private UserService userService;

    @Test
    public void  testSave(){
        //使用注解@ContextConfiguration(classes = Application.class)加载spring容器
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //使用注解注入UserService的Bean
        //UserService userService = (UserService) context.getBean("userService");

        //userService.save();
        System.out.println(userService.price());
    }
}
