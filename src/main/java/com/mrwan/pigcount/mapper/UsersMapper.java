package com.mrwan.pigcount.mapper;

import com.mrwan.pigcount.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表，数据库操作入口
 */
@Mapper
public interface UsersMapper extends com.github.abel533.mapper.Mapper<Users> {

    List<Users> login_in(String username , String password);

    int register(Users users);

    List<Users> code_check(String username , String code );

    List<Users> regiseter_find(Users users);

    int register_update(Users users);

    int code_status(String username);
}