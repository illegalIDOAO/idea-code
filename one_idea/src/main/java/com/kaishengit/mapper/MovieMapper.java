package com.kaishengit.mapper;

import com.kaishengit.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:58 2018/7/10
 */
public interface MovieMapper {

    List<Movie> findByKeys(@Param("name") String name);

    List<Movie> findByIds(@Param("idList") List<Integer> idList);

    List<Movie> findByParam(Map<String ,Object> queryMap);

}
