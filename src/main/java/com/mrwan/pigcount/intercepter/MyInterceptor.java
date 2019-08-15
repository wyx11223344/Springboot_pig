package com.mrwan.pigcount.intercepter;

import com.mrwan.pigcount.utils.sign;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 这个就不用备注是啥吧
 * 签名方法在utils的sign里面
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object object) throws Exception {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        response.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        RequestWrapper request = new RequestWrapper(req);
        String Origin_allow = request.getHeader("Referer");
        if ( Origin_allow.equals("http://localhost:8068/swagger-ui.html") ){
            return true;
        }
        String check = request.getHeader("Access-Control-Request-Headers");
        String signature = request.getParameter("signature");
        String rand = request.getParameter("rand");
        Integer timestamp = Integer.valueOf(request.getParameter("timestamp"));
        if ( check != null ){

        }else {
            if ( signature.equals("") || rand.equals("") || timestamp == null ){
                response.setStatus(201);
                response.getWriter().print("我看你就是个蛤蟆皮");
                response.getWriter();
                response.getWriter();
                return false;
            }
            if ( sign.sign_check(signature , rand , timestamp )){
                response.setStatus(201);
                response.getWriter().print("超时了");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
            HttpSession uuu = request.getSession();
            Object users = uuu.getAttribute("username");
            if (users != null) {
                uuu.setMaxInactiveInterval(1800);
            } else {
                response.setStatus(201);
                response.getWriter().print("您无权访问");
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

