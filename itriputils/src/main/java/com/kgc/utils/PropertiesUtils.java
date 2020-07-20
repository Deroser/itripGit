package com.kgc.utils;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * PropertiesUtils
 * 李文俊
 * 2020.7.20
 */
public class PropertiesUtils {
    private static Properties props;

    /**
     * 加载配置文件
     *
     * @param fileName
     */
    private static void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     *
     * @param key
     * @return
     */
    public static String get(String fileName, String key) {
        readProperties(fileName);
        return props.getProperty(key);
    }
}
