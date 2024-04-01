package com.vins_nerf.core.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Set;

public interface AuthInterceptor extends HandlerInterceptor {
    /**
     * 获取过滤器支持的路径
     *
     * @return 路径集合
     */
    Set<String> getPathPatterns();

    /**
     * 权限鉴权
     *
     * @param request     请求
     * @param requestBody 请求主体
     * @param response    答复
     * @return
     */
    boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) throws IOException;

    /**
     * 判断请求request的signature是否合法
     *
     * @param request     请求
     * @param requestBody 请求主体
     * @param password    密码
     * @param iv          加密向量
     * @return 合法返回true，非法返回false；
     */
    boolean signatureIsLegal(HttpServletRequest request, String requestBody, String password, String iv);
}
