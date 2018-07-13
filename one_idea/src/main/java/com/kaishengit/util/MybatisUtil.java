package com.kaishengit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @Author: chuzhaohui
 * @Date: Created in 19:38 2018/7/9
 */
public class MybatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        // 1.读取mybatis主配置文件
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2.创建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        //方法二
        /*InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);*/
    }

    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession(boolean autoCommit){
        return sqlSessionFactory.openSession(autoCommit);
    }

    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }

}
