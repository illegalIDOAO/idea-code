package com.kaishengit.entity;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:40 2018/7/20
 */
public class Student {
    private String name;
    private String age;

    public Student() {
    }

    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}
