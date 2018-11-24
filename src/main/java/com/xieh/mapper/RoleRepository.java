package com.xieh.mapper;

import com.xieh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program security-oauth2
 * @description:
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
