package com.kaishengit.entity;

import org.springframework.stereotype.Repository;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:41 2018/7/17
 */
public class Product {
    private Integer id;
    private String productName;
    private Integer productInventory;

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productInventory=" + productInventory +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }
}
