package com.kaishengit.dao;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:14 2018/7/13
 */
public class UserDaoTestCase {

    @Test
    public void testSave(){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        UserDao userDao = (UserDao)context.getBean("userDao");

       // UserDao userDao2 = (UserDao)context.getBean("userDao2");

        userDao.save();
       // System.out.println(userDao == userDao2);

        context.close();
    }

}
