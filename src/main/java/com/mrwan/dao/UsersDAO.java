package com.mrwan.dao;

import com.mrwan.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDAO extends JpaRepository<Users,Integer> {
}
