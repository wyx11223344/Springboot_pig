package com.mrwan.pigcount.intercepter;

import com.mrwan.pigcount.utils.sign;
import org.json.JSONObject;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这个就不用备注是啥吧
 * 签名方法在utils的sign里面
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object object) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        RequestWrapper request = new RequestWrapper(req);
        String check = request.getHeader("Access-Control-Request-Headers");
        String str = request.getBodyString(request);
        if ( check != null ){

        }else {
            if ( str.equals("") ){
                response.setStatus(500);
                response.getWriter().print("我看你就是个蛤蟆皮");
                response.getWriter();
                response.getWriter();
                return false;
            }
            JSONObject test = new JSONObject(str);
            if ( sign.sign_check(test.getString("signature") , test.getString("rand") , test.getInt("timestamp")) ){
                response.setStatus(500);
                response.getWriter().print("超时了");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

