package com.kaishengit.proxy;

import com.kaishengit.proxy.jdk.MyInvocationHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:02 2018/7/14
 */
public class JdkProxyTest {

    ApplicationContext context;
    @Before
    public void before(){
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testJdk(){
        Xiaomi xiaomi = (Xiaomi)context.getBean("xiaomi");

        MyInvocationHandler invocationHandler = new MyInvocationHandler(xiaomi);

        //动态产生代理类对象
        Sale sale = (Sale) java.lang.reflect.Proxy.newProxyInstance(Xiaomi.class.getClassLoader(),
                Xiaomi.class.getInterfaces()/*此处接口是指代理的实现类的接口，只是因为和被代理的类实现了相同的接口（且要求只能实现一个接口），故而如此获得*/,
                invocationHandler);

        sale.sale();
    }

    //内部类形式
    @Test
    public void testJdk2(){
        Xiaomi xiaomi = (Xiaomi)context.getBean("xiaomi");

        //动态产生代理类对象
        Sale sale = (Sale) java.lang.reflect.Proxy.newProxyInstance(Xiaomi.class.getClassLoader(),
                Xiaomi.class.getInterfaces()/*此处接口是指代理的实现类的接口，只是因为和被代理的类实现了相同的接口（且要求只能实现一个接口），故而如此获得*/,
                new InvocationHandler(){

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("----------");
                        Object result = method.invoke(xiaomi,args);
                        System.out.println("==========");
                        return result;
                    }
                }
        );
        sale.sale();
        System.out.println(sale.getClass().getName());
    }

}
