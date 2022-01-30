package com.ls.test;

import com.ls.config.Resources;
import com.ls.dao.IUserDao;
import com.ls.pojo.User;
import com.ls.sqlsession.SqlSession;
import com.ls.sqlsession.SqlSessionFactory;
import com.ls.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author ls
 * @Description 自定义持久层框架测试
 * @date 2022/1/30 10:44
 **/
public class CustomPersistenceTest {


    @Test
    public void testFindAll() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> list = sqlSession.selectList("com.ls.dao.IUserDao.findAll");
        for (Object o : list) {
            System.out.println(o);
        }
        return;
    }


    @Test
    public void testFindByName() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("zhangsan");
        Object o = sqlSession.selectOne("com.ls.dao.IUserDao.findUserByUserName", user);
        System.out.println(o);
        return;
    }


    @Test
    public void testMapper() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        List<User> resultList = iUserDao.findAll();
        System.out.println("=====================================findAll==========start");
        for (User user : resultList) {
            System.out.println(user);
        }
        System.out.println("=====================================findAll==========end");

        User user = new User();
        user.setUsername("wangwu");
        User user1 = iUserDao.findUserByUserName(user);
        System.out.println(user1);
    }

    @Test
    public void testDelete() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行操作前数据条数

        printUserData(sqlSession);
        User user = new User();
        user.setId(1);
        Integer update = sqlSession.delete("com.ls.dao.IUserDao.deleteById", user);
        System.out.println("影响的数据条数" + update);

        printUserData(sqlSession);
    }


    @Test
    public void testUpdate() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行操作前数据条数

        printUserData(sqlSession);
        User user = new User();
        user.setId(2);
        user.setUsername("chouliu");
        user.setPassword("10010");
        Integer update = sqlSession.update("com.ls.dao.IUserDao.updateById", user);
        System.out.println("影响的数据条数" + update);

        printUserData(sqlSession);
    }

    private void printUserData(SqlSession sqlSession) {
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        List<User> resultList = iUserDao.findAll();
        System.out.println("=====================================findAll==========start");
        for (User user : resultList) {
            System.out.println(user);
        }
        System.out.println("=====================================findAll==========end");
    }

    @Test
    public void testInsert() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        printUserData(sqlSession);
        User user = new User();
        user.setUsername("ls6");
        user.setPassword("ls666");
        Integer result = sqlSession.insert("com.ls.dao.IUserDao.save",user);
        System.out.println("影响的数据条数" + result);

        printUserData(sqlSession);
    }


    @Test
    public void testDeleteMapper() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserMapper = sqlSession.getMapper(IUserDao.class);

        User user = new User();
        user.setId(2);
        Integer result = iUserMapper.deleteById(user);
        System.out.println("影响的数据条数" + result);
    }


    @Test
    public void testUpdateMapper() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserMapper = sqlSession.getMapper(IUserDao.class);

        printUserData(sqlSession);
        User user = new User();
        user.setId(3);
        user.setUsername("d");
        user.setPassword("8888");
        Integer result = iUserMapper.updateById(user);
        System.out.println("影响的数据条数" + result);
        printUserData(sqlSession);
    }


    @Test
    public void testInsertMapper() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserMapper = sqlSession.getMapper(IUserDao.class);

        printUserData(sqlSession);
        User user = new User();
        user.setUsername("dwefwef");
        user.setPassword("8888wefwef");
        Integer result = iUserMapper.save(user);
        System.out.println("影响的数据条数" + result);
        printUserData(sqlSession);
    }



}

