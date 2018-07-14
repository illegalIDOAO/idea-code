package com.kaishengit.proxy.jdk;

import com.kaishengit.proxy.Sale;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:32 2018/7/14
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target){
        this.target = target;
    }

    //动态代理实现类的方法模板
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("-------------------");
        Object result = method.invoke(target,args);
        System.out.println("===================");

        return result;
    }
}
