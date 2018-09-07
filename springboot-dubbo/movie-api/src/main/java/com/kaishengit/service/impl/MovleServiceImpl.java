package com.kaishengit.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kaishengit.service.MovieService;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:43 2018/8/24
 */
@Service(version = "1.0",timeout = 10000)
public class MovleServiceImpl implements MovieService {

    @Override
    public String findById(Integer id) {
        if (id.equals(1)) {
            return "2012";
        } else {
            return "后天";
        }
    }
}
