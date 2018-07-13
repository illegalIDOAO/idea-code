package com.kaishengit.mapper;

import com.kaishengit.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:47 2018/7/10
 */
public interface ProductMapper {

    /**
     * 新增
     * @param product
     * @return 受影响的行数
     */
    int save(Product product);

    /**
     * 新增
     * @param product
     * @return 受影响的行数
     */
    int saveReturnKey(Product product);

    /**
     * 更新
     * @param product
     * @return 受影响的行数
     */
    int update(Product product);

    /**
     * 删除
     * @param id
     * @return 受影响的行数
     */
    int delete(Integer id);

    /**
     * 根据id查找
     * @param id
     * @return product
     */
    Product findById(Integer id);

    /**
     * 查找全部
     * @return productList
     */
    List<Product> findAll();

    /**
     * 分页查找
     * @param start
     * @param pageSize
     * @return productList
     */
    List<Product> findByPage(@Param("start") int start,@Param("pageSize") int pageSize);

    /**
     * 使用map分页查找
     * @param  map<String,Integer>
     * @return productList
     */
    List<Product> findPageByMap(Map<String, Integer> map);

}
