package com.mrwan.pigcount.service.users;

import com.github.pagehelper.PageHelper;
import com.mrwan.pigcount.mapper.UsersMapper;
import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.Users;
import com.mrwan.pigcount.dao.UsersDAO;
import com.mrwan.pigcount.pojo.adminUsers;
import com.mrwan.pigcount.utils.MailUtil;
import com.mrwan.pigcount.utils.code_get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDAO usersDao;

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 登录server
     * @param username
     * @param password
     * @return
     */
    @Override
    public List<Users> login_in(String username , String password){
        List<Users> users = this.usersMapper.login_in(username,password);
        return users;
    }

    /**
     * 注册server
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public int register(String username , String password) throws Exception {
        String code = code_get.code_get();
        Users users = new Users();
        users.setName(username);
        users.setUsername(username);
        users.setPassword(password);
        users.setCreate_time(new Date().getTime() / 1000);
        users.setState(0);
        users.setCode(code);
        List<Users> find_users = this.usersMapper.regiseter_find(users);
        if ( !find_users.isEmpty() ){
            if (find_users.get(0).getState() == 1){
                return -1;
            }else {
                int result = this.usersMapper.register_update(users);
                if (result == 1){
                    new Thread(new MailUtil(username, code)).start();
                    return 1;
                }else {
                    return -400;
                }
            }
        }else {
            int result = this.usersMapper.register(users);
            if (result == 1){
                new Thread(new MailUtil(username, code)).start();
                return 1;
            }else {
                return -200;
            }
        }
    }

    /**
     * 邮箱验证server
     * @param username
     * @param code
     * @return
     */
    @Override
    public int code_check(String username , String code){
        List<Users> users = this.usersMapper.code_check(username,code);
        int date = (int) (new Date().getTime() / 1000);
        if ( !users.isEmpty() ){
            if ( users.get(0).getCreate_time() - date > 1800  ){//判断是否超过30分钟验证
                return -1;
            }else {
                int count = this.usersMapper.code_status(username);
                if (count == 1){
                    return 1;
                }else {
                    return -200;
                }

            }
        }else {
            return 0;
        }
    }

    /**
     * 登录ip保存serve
     * @param req
     * @param username
     * @return 200（成功），0（失败）
     */
    @Override
    public int ip_save(HttpServletRequest req, String username) throws Exception {
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }

        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }

        int check = this.usersMapper.ip_save(ip,username);
        if ( check > 0 ){
            return 200;
        }else {
            return 0;
        }
    }

    /**
     * 条件获取用户信息
     * @param state
     * @param in_stime
     * @param in_etime
     * @param last_stime
     * @param last_etime
     * @return
     * @throws Exception
     */
    @Override
    @Cacheable(value = "userList" ,key = "targetClass + methodName +#page + #pageSize +#state + #in_stime + #in_etime + #last_etime + #last_stime")
    public pageInfoB<Users> user_get(Integer state , Integer in_stime, Integer in_etime, Integer last_stime, Integer last_etime , Integer page, Integer pageSize) throws Exception {
        pageInfoB<Users> pageInfo = null;
        try {
            PageHelper.startPage(page,pageSize);
            List<Users> users = this.usersMapper.user_get(state,in_stime,in_etime,last_stime,last_etime);
            pageInfo = new pageInfoB<Users>(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageInfo;
    }


    /**
     * 登录server
     * @param username
     * @param password
     * @return
     */
    @Override
    public List<adminUsers> login_user(String username , String password){
        List<adminUsers> users = null;
        try {
            users = this.usersMapper.login_user(username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 用户信息修改
     * @param user
     * @return
     */
    @Override
    @CacheEvict(value = "userList" , allEntries = true)
    public int user_change(Users user){
        int count = 0;
        try {
            count = this.usersMapper.user_change(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

}
