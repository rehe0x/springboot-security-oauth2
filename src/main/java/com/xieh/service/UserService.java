package com.xieh.service;

import com.xieh.entity.User;

import java.util.List;

/**
 * @program security-oauth2
 * @description:
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
public interface UserService {

    User findByUsername(String name);

    List<User> findAll();

    void save(User user);

}
