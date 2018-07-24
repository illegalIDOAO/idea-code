package com.kaishengit.service;

import com.kaishengit.entity.Type;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:40 2018/7/24
 */
public interface TypeService {

    /**
     * 查找类型商品类型列表
     * @return
     */
    List<Type> findTypes();
}
