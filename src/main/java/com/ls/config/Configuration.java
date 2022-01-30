package com.ls.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ls
 * @Description: 核心的配置文件类
 */
public class Configuration {

    /**
     * 配置文件中的数据源对象
     */
    private DataSource dataSource;

    /**
     * key：namespace.id
     * value：具体的sql语句
     */
    private Map<String, MapperStatement> mapperStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }
}
