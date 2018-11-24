package com.xieh.service.impl;

import com.xieh.entity.User;
import com.xieh.mapper.UserRepository;
import com.xieh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program security-oauth2
 * @description:
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user){
        userRepository.save(user);
    }


}
