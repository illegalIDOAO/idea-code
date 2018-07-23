package com.kaishengit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chuzhaohui
 * @Date: Created in 13:26 2018/7/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
//@ContextConfiguration(classes = Application.class)
public class BaseTestCase {
}
