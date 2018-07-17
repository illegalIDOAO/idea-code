package com.kaishengit.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:14 2018/7/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class UserDaoTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSave(){
        //使用注解加载spring容器，@ContextConfiguration(locations = "classpath:spring.xml")
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //使用注解@Autowired自动注入
        //UserDao userDao = (UserDao)context.getBean("userDao");

        userDao.save();
    }

}
