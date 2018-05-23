package com.rrtx.simulationclient.filter;

import javax.servlet.*;
import java.io.IOException;

public class SecurityFilter implements Filter{
    @Override
    public void destroy() {
        System.out.println("SecurityFilter destroy......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        System.out.println("SecurityFilter doing......");
        String data = servletRequest.getParameter("data");
        System.out.println("data: " + data);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("SecurityFilter init......");
    }


}
