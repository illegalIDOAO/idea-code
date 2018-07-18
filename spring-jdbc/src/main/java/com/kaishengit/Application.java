package com.kaishengit;

import javafx.beans.property.Property;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:41 2018/7/17
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
@EnableTransactionManagement()
@PropertySource("config.properties")
public class Application {

    @Autowired
    private Environment environment;

    //@Bean注解表示将方法返回值放入容器中,此处注意方法名会类似于xml配置中id，取用时需要和方法名相同，比如jdbcTemplate()的参数
    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    //@Bean//在使用xml配置加载时，此处@注解需要去掉，否则会报错
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }


    /*public void init(){
        Properties properties = new Properties();
        try {
            properties.load(Application.class.getClassLoader().getResourceAsStream("config.properties"));
            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}

