package com.ls.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author ls
 * @Description 解析SqlMapperConfig.xml 到配置文件中
 * @date 2022/1/29 11:55
 **/
public class XmlMapperBuilder {

    /**
     * 主配置文件类
     */
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析
     *
     * @param mapperInputStream
     */
    public void parse(InputStream mapperInputStream) throws DocumentException {
        Document document = new SAXReader().read(mapperInputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        // 解析select标签的元素
        List<Element> selectElements = rootElement.elements("select");
        // 元素填充
        elementFilling(selectElements, namespace);
        List<Element> updateList = rootElement.elements("update");
        // 元素填充
        elementFilling(updateList, namespace);
        List<Element> deleteList = rootElement.selectNodes("//delete");
        // 元素填充
        elementFilling(deleteList, namespace);

        List<Element> insertList = rootElement.selectNodes("//insert");
        elementFilling(insertList, namespace);


    }

    /**
     * 解析对应的sql标签,并把解析后的元素放入到Configuration缓存中去
     *
     * @param elements  元素标签列表
     * @param namespace 明敏空间
     */
    private void elementFilling(List<Element> elements, String namespace) {
        for (Element element : elements) {
            // sql的statementId
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();

            MapperStatement mapperStatement = new MapperStatement();
            mapperStatement.setId(id);
            mapperStatement.setParameterType(paramterType);
            mapperStatement.setResultType(resultType);
            mapperStatement.setSql(sqlText);
            String statementId = namespace + "." + id;
            configuration.getMapperStatementMap().put(statementId, mapperStatement);
        }
    }
}
