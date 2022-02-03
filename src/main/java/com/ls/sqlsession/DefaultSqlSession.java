package com.ls.sqlsession;

import com.ls.config.Configuration;
import com.ls.config.MapperStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author ls
 * @Description SqlSession接口的具体实现类
 * @date 2022/1/29 16:54
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * 根据查询条件查询数据列表
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @param <T>         返回的参数列表
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<T> selectList(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        Executor simpleExecutor = new SimpleExecutor();
        List<Object> result = simpleExecutor.query(configuration, mapperStatement, params);
        return (List<T>) result;
    }

    /**
     * 根据查询条件查询一条数据对象数据
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @param <T>         返回参数对象
     * @return
     * @throws Exception
     */
    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> resultList = selectList(statementId, params);
        if (resultList.size() == 1) {
            return (T) resultList.get(0);
        } else if (resultList.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + resultList.size());
        } else {
            return null;
        }
    }

    /**
     * 根据条件删除数据
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @return
     * @throws Exception
     */
    @Override
    public Integer delete(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        Executor simpleExecutor = new SimpleExecutor();
        Integer result = simpleExecutor.delete(configuration, mapperStatement, params);
        return result;
    }

    /**
     * 根据条件更新数据
     *
     * @param statementId sql的唯一Id标识 namespace.id
     * @param params      参数列表
     * @return
     * @throws Exception
     */
    @Override
    public Integer update(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        Executor simpleExecutor = new SimpleExecutor();
        Integer result = simpleExecutor.update(configuration, mapperStatement, params);
        return result;
    }

    @Override
    public Integer insert(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        Executor simpleExecutor = new SimpleExecutor();
        Integer result = simpleExecutor.update(configuration, mapperStatement, params);
        return result;
    }


    /**
     * 生成接口的代理类
     *
     * @param mapperClass 代理类的全限定类名成
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了 泛型类型参数化处理
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }

                MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
                String sql = mapperStatement.getSql();
                if (sql.startsWith("update")) {
                    // 如果是更新语句
                    return update(statementId, args);
                } else if (sql.startsWith("delete")) {
                    // 如果是删除啊哦做
                    return delete(statementId, args);

                } else if (sql.startsWith("insert")) {
                    // 如果是插入操作
                    return insert(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }

}
