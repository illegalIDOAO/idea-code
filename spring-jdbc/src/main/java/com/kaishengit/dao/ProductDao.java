package com.kaishengit.dao;

import com.kaishengit.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 12:40 2018/7/17
 */
@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Product product){
        String sql = "insert into product (product_name, product_inventory) value (?,?)";
        return jdbcTemplate.update(sql,product.getProductName(),product.getProductInventory());
    }

    public Product queryById(int id){
        String sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
    }

    public List<Product> queryAll(){
        String sql = "select * from product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public int count(){
        String sql = "select count(*) from product";
        return jdbcTemplate.queryForObject(sql, new SingleColumnRowMapper<Long>()).intValue();
    }

    public List<Map<String,Object>> queryMapList(){
        String sql = "select * from product";
        return jdbcTemplate.query(sql, new ColumnMapRowMapper());
    }

}
