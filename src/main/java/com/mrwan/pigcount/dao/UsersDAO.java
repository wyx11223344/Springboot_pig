package com.mrwan.pigcount.dao;

import com.mrwan.pigcount.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDAO extends JpaRepository<Users,Integer> {
}
