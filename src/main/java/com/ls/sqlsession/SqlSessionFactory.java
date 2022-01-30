package com.ls.sqlsession;

/**
 * @author ls
 * @Description 创建SqlSession接口
 * @date 2022/1/29 16:46
 **/
public interface SqlSessionFactory {

    /**
     * 用来创建SqlSession的对象
     *
     * @return SqlSession 返回对象
     */
    public SqlSession openSession();
}
