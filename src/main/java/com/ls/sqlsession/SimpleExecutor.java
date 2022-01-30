package com.ls.sqlsession;

import com.ls.config.Configuration;
import com.ls.config.MapperStatement;
import com.ls.utils.BoundSql;
import com.ls.utils.GenericTokenParser;
import com.ls.utils.ParameterMapping;
import com.ls.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ls
 * @Description 简单的执行器
 * @date 2022/1/29 17:25
 **/
public class SimpleExecutor implements Executor {

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
    @Override
    public <T> List<T> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws Exception {
        // 1.获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.解析sql语句
        // mybatis中的sql语句占位符是#{} 要转换为？
        String sql = mapperStatement.getSql(); //select * from user where username = #{username}
        BoundSql boundSql = getBoundSql(sql);  // boundSql.getSql = select * from user where username = ?

        // 3.获取预处理对象，并封装参数
        String parameterType = mapperStatement.getParameterType();
        Class<?> parameterClass = getClassType(parameterType);
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            String content = parameterMappings.get(i).getContent();
            Field declaredField = parameterClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }


        // 4.执行sql语句
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mapperStatement.getResultType();
        Class<?> resultTypeClazz = getClassType(resultType);
        ArrayList<Object> result = new ArrayList<>();
        // 5.处理结果集映射
        while (resultSet.next()) {
            // 创建返回值得目标对象
            Object o = resultTypeClazz.newInstance();
            // 获取元数据信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 从1开始的，所以这里的i不可以等于0
            for (int i = 1; i <= columnCount; i++) {
                //列名称
                String fieldName = metaData.getColumnName(i);
                Object fieldValue = resultSet.getObject(fieldName);

                // 使用反射完成实体对象属性风主干
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, resultTypeClazz);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null) {
                    writeMethod.invoke(o, fieldValue);
                }
            }
            result.add(o);
        }

        return (List<T>) result;
    }

    /**
     * 根据条件删除数据
     *
     * @param configuration   mybatis的主配置文件信息，包含了数据源等操作配置
     * @param mapperStatement 执行具体的sql映射配置信息
     * @param params          查询参数
     * @return 影响的数据条数
     */
    @Override
    public Integer delete(Configuration configuration, MapperStatement mapperStatement, Object[] params) throws Exception {
        //1.获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.获取预处理器
        // 处理前的sql
        String sql = mapperStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.封装数据，执行sql
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        // 参数封装
        String parameterType = mapperStatement.getParameterType();
        Class<?> parameterClazz = getClassType(parameterType);
        // 获取字段列表  id,username,password 等字段
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {

            String fieldName = parameterMappings.get(i).getContent();
            Field declaredField = parameterClazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
        // 4.处理结果集映射
        int i = preparedStatement.executeUpdate();
        // 5.返回数据
        return i;
    }

    /**
     * 根据条件更新数据
     *
     * @param configuration   mybatis的主配置文件信息，包含了数据源等操作配置
     * @param mapperStatement 执行具体的sql映射配置信息
     * @param params          查询参数
     * @return 影响的数据条数
     */
    @Override
    public Integer update(Configuration configuration, MapperStatement mapperStatement, Object[] params) throws Exception {
        //1.获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.获取预处理器
        String preSql = mapperStatement.getSql();
        BoundSql boundSql = getBoundSql(preSql);
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        // 3.封装数据，执行sql
        String parameterType = mapperStatement.getParameterType();
        Class<?> parameterClazz = getClassType(parameterType);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            String content = parameterMappings.get(i).getContent();
            Field declaredField = parameterClazz.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);  //params[0] 就是我们传递的User对象参数
            preparedStatement.setObject(i+1,o);
        }
        // 4.处理结果集映射
        int i = preparedStatement.executeUpdate();
        // 5.返回数据
        return i;
    }

    /**
     * 获取参数的类型吧
     *
     * @param parameterType 参数的类型
     * @return 返回参数类
     * @throws ClassNotFoundException
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    /**
     * 解析转换sql语句  将#{} 转换为？号操作
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 解析后的sql语句
        String parseSql = genericTokenParser.parse(sql);
        // 获取解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
