package com.kaishengit.dao;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:12 2018/7/13
 */
public class UserDao {

    public UserDao(){
        System.out.println("creat instance");
    }

    public void init(){
        System.out.println("init...");
    }

    public void destroy(){
        System.out.println("destroy...");
    }

    public void save(){
        System.out.println("save...");
    }
}
