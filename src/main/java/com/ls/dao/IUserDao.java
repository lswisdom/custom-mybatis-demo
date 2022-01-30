package com.ls.dao;

import com.ls.pojo.User;

import java.util.List;

/**
 * @author ls
 * @Description 操作用户表的数据Dao层实现类
 * @date 2022/1/29 22:40
 **/
public interface IUserDao {

    List<User> findAll();

    User findUserByUserName(User user);

    Integer deleteById(User user);

    Integer updateById(User user);

    Integer save(User user);
}
