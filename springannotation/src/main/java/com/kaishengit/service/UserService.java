package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:04 2018/7/14
 */
@Service("userService")
public class UserService {

/*    @Autowired
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }*/

    @Autowired
    private UserDao userDao;

    /*@Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }*/

    public void save(){
        userDao.save();
    }

    public int price(){
        System.out.println("原价100");
        return 100;
    }


}
