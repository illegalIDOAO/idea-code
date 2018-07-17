package com.kaishengit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

/**
 * @Author: chuzhaohui
 * @Date: Created in 9:35 2018/7/17
 */
@ContextConfiguration
@ComponentScan
@EnableAspectJAutoProxy
public class Application {
    //@ContextConfiguration,标记为Spring的java配置类
    //@ComponentScan,开启自动扫描（注解），默认当前包及其子包，也可通过basePackage属性手动设置
    //@EnableAspectJAutoProxy,开启基于注解AOP
}
