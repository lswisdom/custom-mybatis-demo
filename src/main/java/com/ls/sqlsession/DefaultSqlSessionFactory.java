package com.ls.sqlsession;

import com.ls.config.Configuration;

/**
 * @author ls
 * @Description SqlSessionFactory的实现类
 * @date 2022/1/29 16:50
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
