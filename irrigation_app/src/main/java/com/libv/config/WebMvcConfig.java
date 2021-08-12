package com.libv.config;

import com.libv.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor)
//                .excludePathPatterns("/gardenManager/login","/gardenManager/setLoginStatus", "/gardener/login", "/expert/login","/gardenManager/gardenAreaBlockInfo/")
//                .addPathPatterns("/question/**", "/gardenManager/**", "/gardener/**", "/expert/**");
    }
}
