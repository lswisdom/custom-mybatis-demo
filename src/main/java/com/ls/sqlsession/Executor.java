package com.ls.sqlsession;

import com.ls.config.Configuration;
import com.ls.config.MapperStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ls
 * @Description 实际操作数据库的执行器接口
 * @date 2022/1/29 17:22
 **/
public interface Executor {

    /**
     * 执行查询操作的接口
     *
     * @param configuration   mybatis的主配置文件信息，包含了数据源等操作配置
     * @param mapperStatement 执行具体的sql映射配置信息
     * @param params          查询参数
     * @param <T>             返回值列表
     * @return
     * @throws Exception
     */
    public <T> List<T> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws Exception;

    /**
     * 根据条件删除数据
     *
     * @param configuration   mybatis的主配置文件信息，包含了数据源等操作配置
     * @param mapperStatement 执行具体的sql映射配置信息
     * @param params          查询参数
     * @return 影响的数据条数
     */
    Integer delete(Configuration configuration, MapperStatement mapperStatement, Object[] params) throws  Exception;

    /**
     * 根据条件更新数据
     *
     * @param configuration mybatis的主配置文件信息，包含了数据源等操作配置
     * @param mapperStatement   执行具体的sql映射配置信息
     * @param params        查询参数
     * @return 影响的数据条数
     */
    Integer update(Configuration configuration, MapperStatement mapperStatement, Object[] params) throws Exception;
}
