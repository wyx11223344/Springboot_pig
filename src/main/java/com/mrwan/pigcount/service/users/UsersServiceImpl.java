package com.mrwan.pigcount.service.users;

import com.mrwan.pigcount.mapper.UsersMapper;
import com.mrwan.pigcount.pojo.Users;
import com.mrwan.pigcount.dao.UsersDAO;
import com.mrwan.pigcount.utils.MailUtil;
import com.mrwan.pigcount.utils.code_get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
