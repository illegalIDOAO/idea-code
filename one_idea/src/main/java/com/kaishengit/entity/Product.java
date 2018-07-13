package com.kaishengit.entity;

/**
 * @Author: chuzhaohui
 * @Date: Created in 18:00 2018/7/9
 */
public class Product {
    private int id ;
    private String productName;
    private int  productInventory;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productInventory=" + productInventory +
                '}';
    }

    public int getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(int productInventory) {
        this.productInventory = productInventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }}
