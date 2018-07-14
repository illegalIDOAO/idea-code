package com.kaishengit.proxy;

import com.kaishengit.proxy.jdk.MyInvocationHandler;
import org.junit.Test;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:02 2018/7/14
 */
public class ProxyTest {

    @Test
    public void testSale(){

        Xiaomi xiaomi = new Xiaomi();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(xiaomi);

        //动态产生代理类对象
        Sale sale = (Sale) java.lang.reflect.Proxy.newProxyInstance(Xiaomi.class.getClassLoader(),
                Xiaomi.class.getInterfaces()/*此处接口是指代理的实现类的接口，只是因为和被代理的类实现了相同的接口（且要求只能实现一个接口），故而如此获得*/,
                invocationHandler);

        sale.sale();
    }



}
