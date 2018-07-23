package com.kaishengit.service;

import com.kaishengit.BaseTestCase;
import com.kaishengit.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:40 2018/7/17
 */
public class ProductServiceTest extends BaseTestCase {

    @Autowired
    private ProductService productService;

    @Test
    public void testSaveList() throws Exception{
        //测试事务回滚
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setProductName("硬盘");
        product2.setProductName(null);
        product1.setProductInventory(100);
        product2.setProductInventory(100);

        List<Product> productList = Arrays.asList(product1,product2);

        productService.saveList2(productList);
        //productService.saveList2(productList);
    }

}
