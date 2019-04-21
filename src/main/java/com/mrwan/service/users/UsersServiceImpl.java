package com.mrwan.service.users;

import com.mrwan.mapper.UsersMapper;
import com.mrwan.pojo.Users;
import com.mrwan.dao.UsersDAO;
import com.mrwan.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDAO usersDao;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Cacheable(value = "usersCache", key = "'users.findAll'")
    public List<Users> findAll() {
        System.out.println("从Mysql中查询");
        List<Users> list = this.usersDao.findAll();
        return list;
    }

    @Override
    @CacheEvict(value = "usersCache", key = "'users.findAll'")
    public List<Users> queryUserByName(String name) {
        System.out.println("缓存删除");
        List<Users> list = this.usersMapper.queryUserByName(name);
        return list;
    }

    public List<Users> getAll() {
        return usersMapper.getAll();
    }

    // 使用通用Mapper和分页助手
    @Override
    public List<Users> queryUserByPage(Integer page, Integer rows) {
        // 设置分页
        PageHelper.startPage(page, rows);
        // 使用通用Mapper的方法进行查询所有数据
        List<Users> list = this.usersMapper.getAll();
        return list;
    }

}
