package com.ls.config;

import com.alibaba.druid.pool.DruidDataSource;
import jdk.internal.util.xml.SAXParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 解析SqlMapConfig.xml 配置文件对象信息到Configuration对象中
 * @author 77159
 */
public class XmlConfigBuilder {

    /**
     * 核心配置文件类
     */
    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件内容到Configuration中
     * @param inputStream
     */
    public Configuration parse(InputStream inputStream) throws DocumentException {
        // 解析对象为document对象
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        // 1.获取数据源节点,并把数据源的数据添加到Properties中
        Properties properties = new Properties();
        Element dataSource = rootElement.element("dataSource");
        List<Element> propertyList = dataSource.elements("property");
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.put(name,value);
        }

        // 2.创建数据源对象
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("driverClass"));
        druidDataSource.setUrl(properties.getProperty("jdbcUrl"));
        druidDataSource.setUsername(properties.getProperty("username"));
        druidDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(druidDataSource);

        // 3.使用XmlMapperBuilder解析SqlMapperConfig.xml到Configuration对象中
        List<Element> mapperList = rootElement.elements("mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream mapperInputStream = Resources.getResourceAsStream(mapperPath);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperInputStream);
        }
        return configuration;
    }
}
