package com.kaishengit.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:12 2018/7/13
 */
@Repository("userDao")
/*@Scope("prototype")
@Lazy*/
public class UserDao {

    public void save(){
        System.out.println("save...");
    }
}
