package com.vins_nerf.user.auth.impl;

import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("noAuthInterceptor")
public class NoAuthInterceptorImpl extends AuthInterceptorImpl implements AuthInterceptor {
    public NoAuthInterceptorImpl() {
        super(AuthLevel.NO_AUTH);
    }

    @Override
    public boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) {
        return true;
    }
}
