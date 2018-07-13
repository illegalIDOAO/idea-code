package com.kaishengit.entity;

import java.io.Serializable;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:47 2018/7/10
 */
public class School implements Serializable {

    private Integer id ;
    private String schName;

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", schName='" + schName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }
}
