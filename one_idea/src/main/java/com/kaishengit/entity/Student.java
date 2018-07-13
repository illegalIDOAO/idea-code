package com.kaishengit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:09 2018/7/10
 */
public class Student implements Serializable {

    private Integer id ;
    private String stuName;
    private Integer schId;

    private School school;
    private List<Tag> tagList;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuName='" + stuName + '\'' +
                ", schId=" + schId +
                ", school=" + school +
                ", tagList=" + tagList +
                '}';
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }
}
