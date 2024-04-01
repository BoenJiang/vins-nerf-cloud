package com.vins_nerf.user.auth.impl;


import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestHeader;
import com.vins_nerf.core.http.RestSource;
import com.vins_nerf.user.service.SysTokenAuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component("tokenAuthInterceptor")
public class TokenAuthInterceptorImpl extends AuthInterceptorImpl implements AuthInterceptor {
    @Resource
    private SysTokenAuthService sysTokenAuthService;

    @Resource
    private AuthInterceptor baseAuthInterceptor;

    public TokenAuthInterceptorImpl() {
        super(AuthLevel.TOKEN_AUTH);
    }

    @Override
    public boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) throws IOException {
        if (!baseAuthInterceptor.authCheck(request, requestBody, response)) return false;

        // 校验request的x-Aimed-AccessKey是否合法
        String accesskey = request.getHeader(RestHeader.X_ACCESSKEY.getName());
        RestSource restSource = (RestSource) request.getAttribute(RestConstants.SOURCE);
        Map<String, String> tokenAuthMap = sysTokenAuthService.getTokenAuth(restSource, accesskey);
        if (tokenAuthMap == null || tokenAuthMap.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Illegal Accesskey");
            return false;
        }

        // 校验request的签名x-Aimed-Signature是否合法
        String secretkey = tokenAuthMap.get(RestConstants.SECRETKEY);
        String iv = tokenAuthMap.get(RestConstants.IV);
        if (!signatureIsLegal(request, requestBody, secretkey, iv)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Illegal Signature");
            return false;
        }
        request.setAttribute(RestConstants.USER_ID, tokenAuthMap.get(RestConstants.USER_ID));
        request.setAttribute(RestConstants.ACCESSKEY, accesskey);
        return true;
    }
}
