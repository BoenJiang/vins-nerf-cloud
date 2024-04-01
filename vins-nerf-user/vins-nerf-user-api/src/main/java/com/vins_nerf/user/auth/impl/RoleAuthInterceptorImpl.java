package com.vins_nerf.user.auth.impl;

import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component("roleAuthInterceptor")
public class RoleAuthInterceptorImpl extends AuthInterceptorImpl implements AuthInterceptor {

    @Resource
    private AuthInterceptor tokenAuthInterceptor;

    public RoleAuthInterceptorImpl() {
        super(AuthLevel.ROLE_AUTH);
    }

    @Override
    public boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) throws IOException {
        if (!tokenAuthInterceptor.authCheck(request, requestBody, response)) return false;

        //TODO Add role check;
        return true;
    }
}
