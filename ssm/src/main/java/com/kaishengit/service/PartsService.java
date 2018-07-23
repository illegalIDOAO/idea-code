package com.kaishengit.service;

import com.kaishengit.entity.Parts;
import org.springframework.stereotype.Service;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:32 2018/7/23
 */
public interface PartsService {

    /**
    * 根据id查找
    * @param id
    * @return parts对象
    */
    Parts findParsById(int id);
}
