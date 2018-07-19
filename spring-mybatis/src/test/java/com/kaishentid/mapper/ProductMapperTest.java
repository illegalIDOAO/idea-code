package com.kaishentid.mapper;

import com.github.pagehelper.PageHelper;
import com.kaishengit.BaseTest;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.util.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:39 2018/7/19
 */
public class ProductMapperTest extends BaseTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testQuery(){
        Product product = productMapper.selectByPrimaryKey(2);
        System.out.println(product);
    }

    @Test
    public void testQueryForExample(){
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIdBetween(3,8);

        List<Product> productList= productMapper.selectByExample(productExample);
        for(Product product : productList){
            System.out.println(product);
        }
    }

    @Test
    public void testPage(){

        //TODO
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();

        int count = (int)productMapper.countByExample(productExample);

        Page<Product> page = new Page<>(count,2,5);
        int start = page.getStart();
        int end = page.getPageSize()+ page.getStart() -1;

        System.out.println(start);
        System.out.println(end);

        criteria.andIdBetween(start,end);

        List<Product> productList = productMapper.selectByExample(productExample);
        System.out.println("productList的长度为： " + productList.size());
        for(Product product :productList){
            System.out.println(product);
        }


    }


}
