package com.ls.config;

import java.io.InputStream;

/**
 * @author ls
 * @Description 把配置文件转换成流的工具类
 * @date 2022/1/29 11:50
 **/
public class Resources {

    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
