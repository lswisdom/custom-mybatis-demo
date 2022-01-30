package com.ls.utils;

import java.util.List;

/**
 * @author ls
 * @Description 封装处理Sql类
 * @date 2022/1/29 20:40
 **/
public class BoundSql {

    /**
     * 解析过后的sql语句
     */
    private String sql;

    /**
     * ParameterMapping 数组
     */
    private  List<ParameterMapping> parameterMappings;


    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
