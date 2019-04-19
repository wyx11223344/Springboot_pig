package com.mrwan.controller;

import com.mrwan.dao.UsersDAO;
import com.mrwan.pojo.Users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired UsersDAO usersDAO;
    @RequestMapping("/login")
    public List<Users> login() throws Exception{
        List<Users> user = usersDAO.findAll();
        return user;
    }
}
