package com.kaishengit.service;

import com.kaishengit.dao.UserDao;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:04 2018/7/14
 */
public class UserService {

//    public UserService(UserDao userDao){
//        this.userDao = userDao;
//    }

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.save();
    }


}
