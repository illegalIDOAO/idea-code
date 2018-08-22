package com.kaishengit;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author: chuzhaohui
 * @Date: Created in 13:08 2018/8/13
 */
public class App {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.start();

        System.out.println("容器启动成功...");
        System.in.read();
    }
}
