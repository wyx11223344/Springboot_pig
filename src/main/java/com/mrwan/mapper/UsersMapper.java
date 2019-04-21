package com.mrwan.mapper;

import com.mrwan.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UsersMapper extends com.github.abel533.mapper.Mapper<Users> {
    @Select("select * from pig_users where name like '%${value}%'")
    public List<Users> queryUserByName(String name);
    /**
     * 获取所有的user对象
     *
     * @return
     */
    List<Users> getAll();
}