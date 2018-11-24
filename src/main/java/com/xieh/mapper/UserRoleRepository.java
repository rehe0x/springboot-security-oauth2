package com.xieh.mapper;

import com.xieh.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program security-oauth2
 * @description:
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    List<UserRole> findByuid(int uid);

    @Query(value = "select r.role_name from user_role ur left join role r on ur.rid=r.id where ur.uid = ?1",nativeQuery = true)
    List<String> findRoleName(int uid);
}
