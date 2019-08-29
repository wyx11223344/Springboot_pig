package com.mrwan.pigcount.service.users;

import com.github.pagehelper.PageInfo;
import com.mrwan.pigcount.pojo.Users;
import com.mrwan.pigcount.pojo.adminUsers;
import com.mrwan.pigcount.pojo.pageInfoB;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UsersService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    List<Users> login_in(String username , String password);

    /**
     * 注册
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    int register(String username , String password) throws Exception;

    /**
     * 用户激活
     * @param username
     * @param code
     * @return
     */
    int code_check(String username , String code );

    /**
     * 保存ip地址
     * @param req
     * @param username
     * @return
     * @throws Exception
     */
    int ip_save(HttpServletRequest req, String username) throws Exception;

    /**
     * 用户条件查询
     * @param state
     * @param in_stime
     * @param in_etime
     * @param last_stime
     * @param last_etime
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    pageInfoB<Users> user_get(Integer state , Integer in_stime, Integer in_etime, Integer last_stime, Integer last_etime, Integer page, Integer pageSize) throws Exception;


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    List<adminUsers> login_user(String username , String password);

    /**
     * 用户信息修改
     * @param user
     * @return
     */
    int user_change(Users user);

}
