package cn.mo.blog.intercepter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {


        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            //没输密码直接访问，直接重定向到/admin登录页面
            return false;
        }
        return true;
    }
}
