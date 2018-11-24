package com.xieh.service;

import java.util.List;

/**
 * @program security-oauth2
 * @description:
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
public interface UserRoleService {

    List<String> findRoles(int uid);
}
