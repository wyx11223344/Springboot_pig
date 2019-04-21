package com.mrwan.controller;

import com.mrwan.pojo.Users;
import java.util.List;
import com.mrwan.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RestController
    @RequestMapping("loginc")
    public class UsersControlelr {
        @Autowired
        private UsersService usersService;
        @RequestMapping("login_on")
        public List<Users> getUsersAll() {
            List<Users> list = this.usersService.findAll();
            return list;
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

        @RequestMapping("getname")
        public List<Users> getname(@PathVariable Integer page, @PathVariable Integer rows) {
            List<Users> list = this.usersService.queryUserByPage(page, rows);
            return list;
        }

    }

}
