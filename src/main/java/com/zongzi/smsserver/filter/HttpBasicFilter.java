package com.zongzi.smsserver.filter;

import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Order(1)
@WebFilter(filterName = "basicAuthFilter", urlPatterns = "/test/**")
public class HttpBasicFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = ((HttpServletResponse)servletResponse);
        String authorization = request.getHeader("authorization");
        if (StringUtils.isEmpty(authorization)){
            response.addHeader("WWW-Authenticate", "Basic realm=自定义登录");
            response.sendError(401, "need basic auth");
        }else{
            System.out.println(authorization);
            String[] tempArrays = authorization.split(" ");
            if (tempArrays.length == 2 && "Basic".equals(tempArrays[0])){
                Base64.Decoder decoder = Base64.getDecoder();
                String userNameAndPassword = new String(decoder.decode(tempArrays[1]), "utf-8");
                System.out.println("userNameAndPassword: " + userNameAndPassword);
                String[] array1 = userNameAndPassword.split(":");
                if (array1.length == 2){
                    String userName = array1[0];
                    String password = array1[1];
                    //数据库操作
                    if (!("zhong".equals(userName) && "123".equals(password))){
                        response.sendError(403, "用户名或密码错误");
                    }else{
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                }

            }else{
                response.addHeader("WWW-Authenticate", "Basic realm=自定义登录");
                response.sendError(401, "need basic auth");
            }
        }

    }

}
