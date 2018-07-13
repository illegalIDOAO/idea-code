package com.kaishengit.entity;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:53 2018/7/10
 */
public class Tag {

    private Integer id ;
    private String tagName;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
