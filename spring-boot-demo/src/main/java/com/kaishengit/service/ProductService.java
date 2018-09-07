package com.kaishengit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:12 2018/8/23
 */
@Service
@CacheConfig(cacheNames = "product")
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
/*    @Autowired
    private CacheManager cacheManager;*/

    /*@PostConstruct//该注解表示类被加载时运行一次，用于初始化
    public void init(){
        Cache cache = cacheManager.getCache("product");

        Product product = new Product();
        product.setId(3);
        product.setProductName("se");
        cache.put(3,product);

        logger.info("热数据加载完毕");
    }*/


    @Cacheable
    public Product selectById(Integer id){
        return productMapper.selectByPrimaryKey(id);
    }
    @CacheEvict
    public void delById(Integer id){ }


    public Integer count(){
        String sql = "select count(*) from product";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public List<Product> selectByPage() {
        PageHelper.startPage(1,5);
        List<Product> productList = productMapper.selectByExample(new ProductExample());
        return productList;
    }

    public PageInfo<Product> selectByPage2() {
        PageHelper.startPage(1,5);
        List<Product> productList = productMapper.selectByExample(new ProductExample());
        return new PageInfo<>(productList);
    }

}
