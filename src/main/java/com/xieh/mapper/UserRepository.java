package com.xieh.mapper;

import com.xieh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program security-oauth2
 * @description:
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String name);

}
