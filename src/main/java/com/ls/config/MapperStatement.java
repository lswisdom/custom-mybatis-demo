package com.ls.config;

/**
 * @author ls
 * @Description 存储SQL配置文件的映射信息
 * @date 2022/1/29 12:08
 **/
public class MapperStatement {

    /**
     * Sql的StatementId
     */
    private String id;

    /**
     * SQL的返回值类型
     */
    private String resultType;

    /**
     * SQL的参数类型
     */
    private String parameterType;

    /**
     * sql语句
     */
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
