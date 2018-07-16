package com.kaishengit.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:52 2018/7/16
 */
public class CglibProxyTest {

    @Test
    public void testCglib(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Huawei.class);
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * @param target 目标对象
             * @param method 暂时没用
             * @param args 方法参数列表
             * @param methodProxy  产生父类方法的代理方法对象
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("-----------");
                Object result = methodProxy.invokeSuper(target,args);
                System.out.println("===========");
                return result;
            }
        });
        Huawei huawei = (Huawei) enhancer.create();

        System.out.println(huawei.getClass().getName());
        huawei.sale();
    }
}
