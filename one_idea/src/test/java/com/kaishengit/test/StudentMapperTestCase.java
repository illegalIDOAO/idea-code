package com.kaishengit.test;

import com.kaishengit.util.MybatisUtil;
import com.kaishengit.entity.Student;
import com.kaishengit.entity.Tag;
import com.kaishengit.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:59 2018/7/10
 */
public class StudentMapperTestCase {

    Logger logger = LoggerFactory.getLogger(StudentMapperTestCase.class);

    private SqlSession sqlSession;
    private StudentMapper studentMapper;

    @Before
    public void before() {
        sqlSession = MybatisUtil.getSqlSession();

        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }
    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testFindWithSchoolByIdForAs(){
        Student student = studentMapper.findWithSchoolByIdForAs(5);
        logger.debug("student:{}",student);
        sqlSession.close();

        SqlSession sqlSession2 = MybatisUtil.getSqlSession();
        StudentMapper studentMapper2 = sqlSession2.getMapper(StudentMapper.class);
        Student Student2 = studentMapper2.findWithSchoolByIdForAs(5);
        sqlSession2.close();
    }

    @Test
    public void testFindWithSchoolByIdForMap() {

        Student student = studentMapper.findWithSchoolByIdForMap(6);
        logger.debug("student:{}", student);
    }

    @Test
    public void testFindWithTagById(){
        Student student = studentMapper.findWithTagById(1);
        logger.debug("student:{}",student);
    }

    @Test
    public void testAddBatchTag(){
        Tag tag1 = new Tag();
        tag1.setTagName("阳光集结号");

        Tag tag2 = new Tag();
        tag2.setTagName("中国好声音");

        List<Tag> tagLsit = Arrays.asList(tag1,tag2);

        int accout = studentMapper.addBatchTag(tagLsit);
        logger.debug("{}行受影响",accout);

        sqlSession.commit();
    }

}



