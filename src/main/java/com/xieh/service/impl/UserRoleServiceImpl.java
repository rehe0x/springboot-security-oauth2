package com.xieh.service.impl;

import com.xieh.mapper.UserRoleRepository;
import com.xieh.service.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public List<String> findRoles(int uid) {
        List<String> list = userRoleRepository.findRoleName(uid);

        return list;
    }
}
