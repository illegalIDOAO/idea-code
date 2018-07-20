package com.kaishengit.entity;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:24 2018/7/20
 */
public class User {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}