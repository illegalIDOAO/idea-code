package com.kaishengit.service;

import com.kaishengit.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:47 2018/7/14
 */
public class ValueService {

    private Integer age;
    private String name;
    private List<String> list;
    private Set<Double> set;
    private Map<String,String> map;
    private Properties properties;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<Double> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<String> getList() {
        return list;
    }

    public Set<Double> getSet() {
        return set;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public Properties getProperties() {
        return properties;
    }


}
