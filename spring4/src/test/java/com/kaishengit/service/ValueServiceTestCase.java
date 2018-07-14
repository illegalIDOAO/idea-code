package com.kaishengit.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLOutput;
import java.util.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:59 2018/7/14
 */
public class ValueServiceTestCase {

    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ValueService valueService = (ValueService)context.getBean("valueService");

        System.out.println(valueService.getName());
        System.out.println(valueService.getAge());

        for (String str : valueService.getList()){
            System.out.println(str);
        }

        for (Double num : valueService.getSet()){
            System.out.println(num);
        }

        for(Map.Entry<String,String> entry : valueService.getMap().entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        Properties props = valueService.getProperties();
        Enumeration<Object> keys = props.keys();
        while (keys.hasMoreElements()){
            Object key = keys.nextElement();
            System.out.println(key + ":" + props.get(key));
        }
    }
}
