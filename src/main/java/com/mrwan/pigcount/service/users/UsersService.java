package com.mrwan.pigcount.service.users;

import com.mrwan.pigcount.pojo.Users;
import com.sun.net.httpserver.HttpsServer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UsersService {
    List<Users> login_in(String username , String password);

    int register(String username , String password) throws Exception;

    int code_check(String username , String code );

    int ip_save(HttpServletRequest req, String username) throws Exception;
}
