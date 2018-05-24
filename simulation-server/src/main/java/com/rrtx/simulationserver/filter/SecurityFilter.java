package com.rrtx.simulationserver.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rrtx.simulationserver.util.SHA256;

import javax.servlet.*;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("SecurityFilter destroy......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("SecurityFilter doing......");
        String data = servletRequest.getParameter("formData");
        JSONObject jsonObject = JSON.parseObject(data);
        String mac = SHA256.getSHA256Str(jsonObject.getString("data"));

        System.out.println(mac);
        System.out.println(jsonObject.getString("mac"));
        if(!mac.equals(jsonObject.getString("mac"))){
            System.out.println("mac validate failed!");
            return ;
        }

        System.out.println("data: " + data);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("SecurityFilter init......");
    }


}
