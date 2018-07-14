package com.kaishengit.proxy;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:45 2018/7/14
 */
public class Xiaomi implements Sale{


    @Override
    public void sale() {
        System.out.println("小米出貨");
    }
}
