package com.mrwan.pigcount.controller;

import com.mrwan.pigcount.pojo.Users;
import java.util.List;
import com.mrwan.pigcount.service.users.UsersService;
import com.mrwan.pigcount.utils.BaseResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RestController
    @RequestMapping("loginc")
    public class UsersControlelr {
        @Autowired
        private UsersService usersService;
        @RequestMapping("login_in")
        public BaseResponseInfo getUsersAll(
                @RequestParam(value = "username", required = false) String username,
                @RequestParam(value = "password", required = false) String password
        ) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                if (this.usersService.login_in(username , password)){
                    res.code = 200;
                    res.msg = "登录成功";
                }else {
                    res.code = 1;
                    res.msg = "账号密码错误";
                }
            } catch(Exception e){
                e.printStackTrace();
                res.code = 500;
                res.msg = "登录出错";
            }
            return res;
        }

        @RequestMapping("list/{name}")
        public List<Users> queryUserAll(@PathVariable String name) {
            List<Users> list = this.usersService.queryUserByName(name);
            return list;
        }

        @RequestMapping("/getallusers")
        public List<Users> getAllUsers() {
            return usersService.getAll();
        }

        @RequestMapping("list/{page}/{rows}")
        public List<Users> queryUserAll(@PathVariable Integer page, @PathVariable Integer rows) {
            List<Users> list = this.usersService.queryUserByPage(page, rows);
            return list;
        }

    }

}
