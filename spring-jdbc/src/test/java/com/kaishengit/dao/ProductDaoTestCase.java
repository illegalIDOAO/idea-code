package com.kaishengit.dao;

import com.kaishengit.BaseTestCase;
import com.kaishengit.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: chuzhaohui
 * @Date: Created in 13:28 2018/7/17
 */
public class ProductDaoTestCase extends BaseTestCase {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testSave(){
        Product product = new Product();
        product.setProductName("主机");
        product.setProductInventory(100);
        int num = productDao.save(product);
        Assert.assertEquals(1,num);
    }

    @Test
    public void testQueryById(){
        int id = 6;
        Product product = productDao.queryById(id);
        System.out.println(product);
        Assert.assertNotNull(product);
    }

    @Test
    public void testQueryAll(){
        List<Product> productList = productDao.queryAll();
        for(Product product : productList){
            System.out.println(product);
        }
        //Assert.assertEquals(8,productList.size());
    }

    @Test
    public void testCount(){
        int count = productDao.count();
        Assert.assertEquals(8,count);
    }

    @Test
    public void testQuieryMapList(){
        List<Map<String,Object>> mapList = productDao.queryMapList();

        for(Map map : mapList){
            Set<Map.Entry<String,Object>> entryList = map.entrySet();
            for(Map.Entry entry : entryList){
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }

}
