package com.kaishengit.test;

import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:58 2018/7/10
 */
public class ProductMapperTestCase {

    Logger logger = LoggerFactory.getLogger(ProductMapperTestCase.class);

    private SqlSession sqlSession;
    private ProductMapper productMapper;
    @Before
    public void before(){
        sqlSession = MybatisUtil.getSqlSession();
        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        productMapper = sqlSession.getMapper(ProductMapper.class);// 获得接口的实现类对象
    }

    @Test
    public void testSave(){
        Product product = new Product();
        product.setProductName("小米");
        product.setProductInventory(100);

        int count = productMapper.save(product);
        logger.debug("受影响的行数 : {}" ,count);

        sqlSession.commit();
    }

    @Test
    public void testSaveReturnKey(){
        Product product = new Product();
        product.setProductName("小米");
        product.setProductInventory(100);

        int count = productMapper.saveReturnKey(product);
        int id = product.getId();

        logger.debug("受影响的行数 ：{}" ,count);
        logger.debug("主键 ：{}" ,id);

        sqlSession.commit();
    }

    @Test
    public void testDelete(){
        int count = productMapper.delete(4);
        logger.debug("受影响的行数 ：{}" ,count);
        sqlSession.commit();
    }

    @Test
    public void testUpdate(){
        Product product = productMapper.findById(5);
        product.setProductName("华为");

        int count = productMapper.update(product);
        logger.debug("受影响的行数 ：{}" ,count);

        sqlSession.commit();
    }

    @Test
    public void testFindById(){
        Product product = productMapper.findById(5);
        logger.debug("product为：{}", product.toString());
    }

    @Test
    public void testFindAll(){
        List<Product> productList = productMapper.findAll();
        for(Product product : productList){
            logger.debug("product为：{}", product.toString());
        }
    }

    @Test
    public void testFindByPage(){
        List<Product> productList = productMapper.findByPage(0,3);
        for(Product product : productList){
            logger.debug("product为：{}", product.toString());
        }
    }

    @Test
    public void testFindPageByMap() {
        Map<String ,Integer> map = new HashMap<>();
        map.put("start",1);
        map.put("pageSize",3);

        List<Product> productList = productMapper.findPageByMap(map);
        for(Product product : productList){
            logger.debug("product为：{}", product.toString());
        }
    }


    @After
    public void after(){
        sqlSession.close();
    }
}
