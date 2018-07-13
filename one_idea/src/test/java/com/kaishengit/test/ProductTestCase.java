package com.kaishengit.test;

import com.kaishengit.util.MybatisUtil;
import com.kaishengit.entity.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 19:43 2018/7/9
 */
public class ProductTestCase {

    @Test
    public void testSave() throws IOException{
        //读取配置文件
        Reader reader = Resources.getResourceAsReader("mybatis.xml");
        //InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");

        //创建SqlSessionFactory
        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        //获得Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //操作数据库
        Product product = new Product();
        product.setProductName("手机");
        product.setProductInventory(200);

        int res = sqlSession.insert("com.kaishengit.mapper.ProductMapper.save",product);

        //断言
        //Assert.assertEquals(1,res);

        //释放资源
        sqlSession.close();
    }

    @Test
    public void testAdd(){
       SqlSession sqlSession = MybatisUtil.getSqlSession(true);
       Product product = new Product();
       product.setProductName("手机");
       product.setProductInventory(200);
       sqlSession.insert("com.kaishengit.mapper.ProductMapper.save",product);
       sqlSession.close();
    }

    @Test
    public void testFind(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        Product product = sqlSession.selectOne("com.kaishengit.mapper.ProductMapper.findById",1);
        System.out.println(product);
        sqlSession.close();
    }
    @Test
    public void testFindAll(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        List<Product> productList = sqlSession.selectList("com.kaishengit.mapper.ProductMapper.findAll");
        for(Product product : productList){
            System.out.println(product);
        }
        sqlSession.close();
    }

    @Test
    public void testUpdate(){
        SqlSession sqlSession = MybatisUtil.getSqlSession(true);
        Product product = sqlSession.selectOne("com.kaishengit.mapper.ProductMapper.findById",2);
        System.out.println(product);
        product.setProductName("电脑");
        product.setProductInventory(150);
        sqlSession.update("com.kaishengit.mapper.ProductMapper.update",product);
        sqlSession.close();
    }

    @Test
    public void testDelete(){
        SqlSession sqlSession = MybatisUtil.getSqlSession(true);
        sqlSession.delete("com.kaishengit.mapper.ProductMapper.del",1);
        sqlSession.close();
    }


}
