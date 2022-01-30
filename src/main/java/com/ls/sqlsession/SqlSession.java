package com.ls.sqlsession;

import java.util.List;

/**
 * @author ls
 * @Description SqlSession的接口类, 封装了具体调用Sql的JDBC的操作
 * @date 2022/1/29 16:52
 **/
public interface SqlSession {

    /**
     * 根据查询条件查询数据列表
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @param <T>         返回的参数列表
     * @return
     * @throws Exception
     */
    public <T> List<T> selectList(String statementId, Object... params) throws Exception;

    /**
     * 根据查询条件查询一条数据对象数据
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @param <T>         返回参数对象
     * @return
     * @throws Exception
     */
    public <T> T selectOne(String statementId, Object... params) throws Exception;

    /**
     * 生成接口的代理类
     *
     * @param mapperClass 代理类的全限定类名成
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<?> mapperClass);

    /**
     * 根据条件删除数据
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @return
     * @throws Exception
     */
    public Integer delete(String statementId, Object... params) throws Exception;

    /**
     * 根据条件更新数据
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @return
     * @throws Exception
     */
    public Integer update(String statementId, Object... params) throws Exception;

    /**
     * 插入数据
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params 参数列表
     * @return
     * @throws Exception
     */
    public Integer insert(String statementId,Object ...params) throws Exception;
}
