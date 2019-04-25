package com.mrwan.pigcount.mapper;

import com.mrwan.pigcount.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表，数据库操作入口
 */
@Mapper
public interface UsersMapper extends com.github.abel533.mapper.Mapper<Users> {

    /**
     * 登录查询数据库
     * @param username
     * @param password
     * @return
     */
    List<Users> login_in(@Param("username") String username ,@Param("password") String password);

    /**
     *
     * @param users
     * @return
     */
    int register(Users users);

    /**
     *
     * @param username
     * @param code
     * @return
     */
    List<Users> code_check(@Param("username") String username ,@Param("code") String code );

    /**
     *
     * @param users
     * @return
     */
    List<Users> regiseter_find(Users users);

    /**
     *
     * @param users
     * @return
     */
    int register_update(Users users);

    /**
     * code验证成功，更新状态
     * @param username
     * @return
     */
    int code_status(@Param("username") String username);
}