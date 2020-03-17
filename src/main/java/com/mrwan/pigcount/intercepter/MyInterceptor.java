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
        RequestWrapper request = new RequestWrapper(req);
        String Origin_allow = request.getHeader("referer");
        if (Origin_allow != null){
            if ( Origin_allow.equals("http://localhost:8068/swagger-ui.html") || Origin_allow.equals("http://pigshop.mrwanmisshen.com/swagger-ui.html") ){
                return true;
            }
        }
        String check = request.getHeader("Access-Control-Request-Headers");
        String signature = request.getParameter("signature");
        String rand = request.getParameter("rand");
        Integer timestamp = Integer.valueOf(request.getParameter("timestamp"));
        Boolean noLog = Boolean.valueOf(request.getParameter("noLog"));
        if ( check != null ){

        }else {
            if ( signature.equals("") || rand.equals("") || timestamp == null ){
                response.setStatus(201);
                response.getWriter().print("我看你就是个蛤蟆皮");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
            if ( sign.sign_check(signature , rand , timestamp )){
                response.setStatus(201);
                response.getWriter().print("超时了");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
            if (!noLog){
                HttpSession uuu = request.getSession();
                Object users = uuu.getAttribute("username");
                System.out.println(users);
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

