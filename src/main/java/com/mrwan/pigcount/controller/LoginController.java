package com.mrwan.pigcount.controller;

import com.mrwan.pigcount.pojo.Users;
import java.util.List;
import com.mrwan.pigcount.service.users.UsersService;
import com.mrwan.pigcount.utils.BaseResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @RestController
    @RequestMapping(value = "loginc" , method = RequestMethod.POST)
    public class UsersControlelr {
        @Autowired
        private UsersService usersService;

        /**
         * 用户登录
         * @param username
         * @param password
         * @param session
         * @return
         * @throws Exception
         */
        @RequestMapping("login_in")
        public BaseResponseInfo getUsersAll(
                @RequestParam(value = "username", required = false) String username,
                @RequestParam(value = "password", required = false) String password,
                HttpSession session
        ) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                List<Users> users = this.usersService.login_in(username , password);
                if ( !users.isEmpty() ){
                    if ( users.get(0).getState() == 1 ){
                        //创建Session对象保存User对象
                        session.setAttribute("username", users);
                        session.setMaxInactiveInterval(1800);
                        res.code = 200;
                        res.msg = "登录成功";
                        res.data = users;
                    }else if( users.get(0).getState() == 0 ){
                        res.code = 100;
                        res.msg = "账号未激活";
                    }
                }else {
                    res.code = 1;
                    res.msg = "账号密码错误";
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return res;
        }

        /**
         * 用户登录状态判断
         * @param request
         * @return
         * @throws Exception
         */
        @RequestMapping("login_on")
        public BaseResponseInfo Users_login_on(
                HttpServletRequest request
        ) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                HttpSession uuu = request.getSession();
                Object users = uuu.getAttribute("username");
                if (users != null) {
                    uuu.setMaxInactiveInterval(1800);
                    res.code = 200;
                    res.msg = "登录验证成功";
                    res.data = users;
                } else {
                    res.code = 1;
                    res.msg = "未登录或登录超时";
                    res.data = null;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return res;
        }

        /**
         * 用户登出
         * @param request
         * @return
         * @throws Exception
         */
        @RequestMapping("login_out")
        public BaseResponseInfo User_login_out(
                HttpServletRequest request
        ) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                HttpSession uuu = request.getSession();
                Object users = uuu.getAttribute("username");
                if (users != null) {
                    uuu.setMaxInactiveInterval(1);
                    res.code = 200;
                    res.msg = "退出登录成功";
                    res.data = users;
                } else {
                    res.code = 1;
                    res.msg = "未登录或登录超时，无需退出";
                    res.data = null;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return res;
        }

        /**
         * 用户注册
         * @param username
         * @param password
         * @return
         * @throws Exception
         */
        @RequestMapping("register")
        public BaseResponseInfo register(
                @RequestParam(value = "username", required = false) String username,
                @RequestParam(value = "password", required = false) String password
        ) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                if ( username.equals("") || password.equals("") ){
                    res.code = -1;
                    res.msg = "账号密码不能为空";
                }else {
                    int check = this.usersService.register(username , password);
                    if ( check == 1 ){
                        res.code = 200;
                        res.msg = "注册成功,请立即进行邮箱验证";
                    }else if (check == -1){
                        res.code = -200;
                        res.msg = "该账号已被注册";
                    }else {
                        res.code = 1;
                        res.msg = "注册失败";
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return res;
        }

        /**
         * 邮箱验证
         * @param username
         * @param code
         * @return
         * @throws Exception
         */
        @RequestMapping("code_check")
        public BaseResponseInfo code_check(
                @RequestParam(value = "username", required = false) String username,
                @RequestParam(value = "code", required = false) String code
        ) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                if ( code.equals("") || username.equals("") ){
                    res.code = -1;
                    res.msg = "验证信息出错";
                }else {
                    int code_int = this.usersService.code_check(username , code);
                    if ( code_int == 1 ){
                        res.code = 200;
                        res.msg = "邮箱验证成功！";
                    }else if ( code_int == 0 ){
                        res.code = 1;
                        res.msg = "验证信息出错,请使用最新的验证邮件";
                    }else if ( code_int == -1 ){
                        res.code = -1;
                        res.msg = "邮箱验证超时！";
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return res;
        }

    }

}
