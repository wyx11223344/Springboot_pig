package com.mrwan.service.users;

import com.mrwan.pojo.Users;
import java.util.List;

public interface UsersService {
    List<Users> findAll();

    List<Users> queryUserByName(String name);

    List<Users> getAll();

    List<Users> queryUserByPage(Integer page, Integer rows);
}
