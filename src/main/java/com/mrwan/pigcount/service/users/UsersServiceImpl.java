package com.mrwan.pigcount.service.users;

import com.mrwan.pigcount.mapper.UsersMapper;
import com.mrwan.pigcount.pojo.Users;
import com.mrwan.pigcount.dao.UsersDAO;
import com.mrwan.pigcount.utils.MailUtil;
import com.mrwan.pigcount.utils.code_get;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (req == null) {
            throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
        }
        String ipString = req.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = req.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = req.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = req.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }

        if("0:0:0:0:0:0:0:1".equals(ipString)){
            ipString = "127.0.0.1";
        }

        int check = this.usersMapper.ip_save(ipString,username);
        if ( check > 0 ){
            return 200;
        }else {
            return 0;
        }
    }

}
