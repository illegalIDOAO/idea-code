package com.kaishengit.test;

import com.kaishengit.util.MybatisUtil;
import com.kaishengit.entity.Movie;
import com.kaishengit.mapper.MovieMapper;
import com.kaishengit.mapper.StudentMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:59 2018/7/10
 */
public class MovieMapperTestCase {

    Logger logger = LoggerFactory.getLogger(MovieMapperTestCase.class);

    private SqlSession sqlSession;
    private MovieMapper movieMapper;

    @Before
    public void before() {
        sqlSession = MybatisUtil.getSqlSession();

        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        movieMapper = sqlSession.getMapper(MovieMapper.class);
    }
    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testFindByKeys(){
        String name = "大";
        List<Movie> movieList = movieMapper.findByKeys(name);

        logger.debug("共找到：{}个结果",movieList.size());
        for(Movie movie : movieList) {
            logger.debug("电影：{}", movie);
        }
    }

    @Test
    public void testFindByIds(){
        List<Integer> idList = Arrays.asList(1,2,3);
        List<Movie> movieList = movieMapper.findByIds(idList);

        logger.debug("共找到：{}个结果",movieList.size());
        for(Movie movie : movieList) {
            logger.debug("电影：{}", movie);
        }
    }

    @Test
    public void testFindByParam(){
        Map<String,Object> map = new HashMap<>();
        map.put("name", "大");
        map.put("director","周");

        List<Movie> movieList = movieMapper.findByParam(map);

        logger.debug("共找到：{}个结果",movieList.size());
        for(Movie movie : movieList) {
            logger.debug("电影：{}", movie);
        }
    }


}
