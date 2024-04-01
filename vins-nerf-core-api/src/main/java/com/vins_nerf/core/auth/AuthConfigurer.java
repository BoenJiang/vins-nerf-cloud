package com.vins_nerf.core.auth;

import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Slf4j
@Configuration
@NoArgsConstructor
public class AuthConfigurer implements WebMvcConfigurer {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 扫描所有AuthInterceptor类，并添加拦截器
        applicationContext.getBeansOfType(AuthInterceptor.class).forEach((key, value) -> {
            if (value.getPathPatterns() != null) {
                // addInterceptor：添加一个实现HandlerInterceptor了接口的拦截器实例
                // addPathPatterns：拦截的URI；
                // excludePathPatterns: 放行的URI
                registry.addInterceptor(value).addPathPatterns(new ArrayList<>(value.getPathPatterns()));
                log.info(value.getClass().getName() + "_HANDLE_LIST : " + value.getPathPatterns());
            }
        });
    }

}
