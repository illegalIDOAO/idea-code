package com.kaishengit.com.kaishengit.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:12 2018/7/12
 */
public class ProductMapperTestCase {

    Logger logger = LoggerFactory.getLogger(ProductMapperTestCase.class);

    private SqlSession sqlSession;
    private ProductMapper productMapper;

    @Before
    public void before() {
        sqlSession = MybatisUtils.getSqlSession();
        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        // 获得接口的实现类对象
        productMapper = sqlSession.getMapper(ProductMapper.class);
    }

    @After
    public void after() {
        sqlSession.close();
    }

    @Test
    public void testInsert(){
        Product product = new Product();
        product.setProductName("笔记本");
        product.setProductInventory(200);

        int count = productMapper.insertSelective(product);
        logger.debug("受影响的行数为{}",count);
        sqlSession.commit();
    }

    @Test
    public void testFindById(){
        Product product = productMapper.selectByPrimaryKey(9);
        logger.debug("该产品为:{}",product);
    }

    @Test
    public void testAll(){
        ProductExample productExample = new ProductExample();

        //or()
        productExample.or().andIdEqualTo(2);
        productExample.or().andIdEqualTo(3);
        //排序
        productExample.setOrderByClause("id desc");
        //去重
        productExample.setDistinct(true);

        List<Product> listProduct = productMapper.selectByExample(productExample);

        System.out.println("共计产品个数：" + listProduct.size());
        for(Product product : listProduct){
            System.out.println(product);
        }
    }

    @Test
    public void testByExample(){
        ProductExample productExample = new ProductExample();

        /*ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIdBetween(2,8);
        criteria.andProductInventoryLessThan(160);*/
        productExample.createCriteria()
                .andIdBetween(2,9)
                .andProductInventoryLessThanOrEqualTo(150);

        List<Product> listProduct = productMapper.selectByExample(productExample);
        System.out.println("共计产品个数：" + listProduct.size());
        for(Product product : listProduct){
            System.out.println(product);
        }

    }

    @Test
    public void testPage(){
        //PageHelper.startPage(1,3);//从第1页开始，查3条
        PageHelper.offsetPage(1,5);//从第一条开始，查5条

        ProductExample productExample = new ProductExample();
        List<Product> productList = productMapper.selectByExample(productExample);
        System.out.println("productList的长度为： " + productList.size());
        for(Product product :productList){
            System.out.println(product);
        }

        PageInfo<Product> productPageInfo = new PageInfo<>(productList);
        System.out.println("当前页码："+productPageInfo.getPageNum());
        System.out.println("总页码："+productPageInfo.getPages());
        System.out.println("PageSize："+productPageInfo.getPageSize());
        System.out.println("EndRow："+productPageInfo.getEndRow());
        System.out.println("NavigateFirstPage："+productPageInfo.getNavigateFirstPage());
        System.out.println("NextPage："+productPageInfo.getNextPage() );
        System.out.println("Size："+productPageInfo.getSize());
        System.out.println("PageSize："+productPageInfo.getPageSize());
    }

}
