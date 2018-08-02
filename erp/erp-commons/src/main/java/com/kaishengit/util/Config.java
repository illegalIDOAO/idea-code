package com.kaishengit.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: chuzhaohui
 * @Date: Created in 13:16 2018/7/26
 */
public class Config {

    private static Properties pro = new Properties();
    static {
        try {
            pro.load(Constant.class.getClassLoader().getResourceAsStream("commonsConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件
     * @param key
     * @return
     */
    public static String getConfig(String key){
        return pro.getProperty(key);
    }
}
