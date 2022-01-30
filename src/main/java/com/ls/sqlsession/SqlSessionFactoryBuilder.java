package com.ls.sqlsession;

import com.ls.config.Configuration;
import com.ls.config.XmlConfigBuilder;
import org.dom4j.DocumentException;

import java.io.InputStream;

/**
 * @author ls
 * @Description 创建SqlSessionFactory
 * @date 2022/1/29 16:45
 **/
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream inputStream) throws DocumentException {
        // 解析主配置文件,并将主配置文件的内容放入到Configuration中
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(inputStream);

        // 2.根据主配置文件创建SqlSessionFactory对象,并创建SqlSession对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
