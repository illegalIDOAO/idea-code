package com.kaishengit.service.impl;

import com.kaishengit.entity.Parts;
import com.kaishengit.mapper.PartsMapper;
import com.kaishengit.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:36 2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 根据id查找
     *
     * @param id
     * @return parts对象
     */
    @Override
    public Parts findParsById(int id) {
        return partsMapper.selectByPrimaryKey(id);
    }
}
