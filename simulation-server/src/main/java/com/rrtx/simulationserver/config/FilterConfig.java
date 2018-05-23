package com.rrtx.simulationserver.config;

import com.rrtx.simulationserver.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistration(){
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new SecurityFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }
}
