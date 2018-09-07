package com.kaishengit.proxy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:43 2018/7/16
 */
public class AopTestCase {

    @Test
    public void testAopJdk(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Sale sale = (Sale)context.getBean("xiaomi");
        System.out.println("-----------");
        sale.sale();
/*        System.out.println(sale.getClass());
        System.out.println(sale.getClass().getName());*/
        System.out.println("-----------");
        int price = sale.price();
        System.out.println("小米售价:" + price);
    }


    @Test
    public void testAopCglib(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Huawei huawei = (Huawei) context.getBean("huawei");
        System.out.println("-----------");
        huawei.sale();
        /*System.out.println(huawei.getClass());
        System.out.println(huawei.getClass().getName());*/
        System.out.println("-----------");
        int price = huawei.price();
        System.out.println("华为售价:" + price);
    }
}
