package com.mrwan.pigcount.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrwan.pigcount.pojo.Users;
import com.mrwan.pigcount.service.users.UsersService;
import com.mrwan.pigcount.utils.BaseResponseInfo;
import com.mrwan.pigcount.utils.req_change;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class adminUserControl {
    @RestController
    @Api(value="用户数据控制" , tags="用户数据控制")
    @RequestMapping(value = "user" , method = RequestMethod.POST)
    public class UserControlelr {
        @Autowired
        private UsersService usersService;

        /**
         * 用户数据
         * @param page
         * @param pageSize
         * @param state
         * @param in_stime
         * @param in_etime
         * @param last_stime
         * @param last_etime
         * @return
         * @throws Exception
         */
        @ApiOperation(value = "获取用户数据")
        @RequestMapping("user_get")
        public BaseResponseInfo userGet(@RequestParam(value = "page", required = true) Integer page,
                                        @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                        @RequestParam(value = "state", required = false) Integer state,
                                        @RequestParam(value = "in_stime", required = false) Integer in_stime,
                                        @RequestParam(value = "in_etime", required = false) Integer in_etime,
                                        @RequestParam(value = "last_stime", required = false) Integer last_stime,
                                        @RequestParam(value = "last_etime", required = false) Integer last_etime) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                PageHelper.startPage(page,pageSize);
                List<Users> users = this.usersService.user_get(state,in_stime,in_etime,last_stime,last_etime);
                PageInfo<Users> pageInfo = new PageInfo<Users>(users);
                res.data = pageInfo;
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.code = 200;
            res.msg = "获取成功";
            return res;
        }

        @ApiOperation(value = "用户数据修改")
        @RequestMapping("user_change")
        public BaseResponseInfo userChange(@RequestParam(value = "id", required = true) Integer id,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "username", required = false) String username,
                                           @RequestParam(value = "password", required = false) String password,
                                           @RequestParam(value = "create_time", required = false) Long create_time,
                                           @RequestParam(value = "state", required = false) Integer state,
                                           @RequestParam(value = "code", required = false) String code,
                                           @RequestParam(value = "last_time", required = false) Long last_time,
                                           @RequestParam(value = "ip", required = false) String ip) throws Exception {
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                Users user = new Users().toUsers(id,name,username,password,create_time,state,code,last_time,ip);
                int count = this.usersService.user_change(user);
                if ( count >= 1 ){
                    res.code = 200;
                    res.msg = "修改成功";
                }else {
                    res.code = 1;
                    res.msg = "修改失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
    }
}
