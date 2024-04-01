package com.vins_nerf.user.auth.impl;

import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestHeader;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component("defaultAuthInterceptor")
public class DefaultAuthInterceptorImpl extends AuthInterceptorImpl implements AuthInterceptor {
    @Value("${aimed.default.auth.accesskey}")
    private String defaultAuthAK;

    @Value("${aimed.default.auth.secretkey}")
    private String defaultAuthSK;

    @Value("${aimed.default.auth.iv}")
    private String defaultAuthIV;

    @Resource
    private AuthInterceptor baseAuthInterceptor;

    public DefaultAuthInterceptorImpl() {
        super(AuthLevel.DEFAULT_AUTH);
    }

    @Override
    public boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) throws IOException {
        if (!baseAuthInterceptor.authCheck(request, requestBody, response)) return false;

        // 校验request的x-Aimed-AccessKey是否合法
        String accesskey = request.getHeader(RestHeader.X_ACCESSKEY.getName());
        if (!defaultAuthAK.equals(accesskey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Illegal Accesskey");
            return false;
        }

        // 校验request的签名x-Aimed-Signature是否合法
        if (!signatureIsLegal(request, requestBody, defaultAuthSK, defaultAuthIV)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Illegal Signature");
            return false;
        }
        return true;
    }
}
