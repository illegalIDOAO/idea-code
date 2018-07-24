package com.kaishengit.service.impl;

import com.kaishengit.entity.Type;
import com.kaishengit.entity.TypeExample;
import com.kaishengit.mapper.TypeMapper;
import com.kaishengit.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:40 2018/7/24
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 查找类型商品类型列表
     *
     * @return
     */
    @Override
    public List<Type> findTypes() {
        TypeExample typeExample = new TypeExample();
        return typeMapper.selectByExample(typeExample);
    }
}
