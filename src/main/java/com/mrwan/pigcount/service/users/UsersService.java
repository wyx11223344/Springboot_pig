package com.mrwan.pigcount.service.users;

import com.mrwan.pigcount.pojo.Users;
import java.util.List;

public interface UsersService {
    boolean login_in(String username , String password);

    List<Users> findAll();

    List<Users> queryUserByName(String name);

    List<Users> getAll();

    List<Users> queryUserByPage(Integer page, Integer rows);
}
