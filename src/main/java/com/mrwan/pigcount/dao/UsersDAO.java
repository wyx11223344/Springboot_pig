package com.mrwan.pigcount.dao;

import com.mrwan.pigcount.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 数据库操作层
 * 通用方法
 */
public interface UsersDAO extends JpaRepository<Users,Integer> {
}
