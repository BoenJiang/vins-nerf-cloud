package com.vins_nerf.user.auth.impl;

import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestHeader;
import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.http.RestSource;
import com.vins_nerf.core.utils.MD5Util;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.service.SysTokenAuthService;
import com.vins_nerf.user.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component("baseAuthInterceptor")
public class BaseAuthInterceptorImpl extends AuthInterceptorImpl implements AuthInterceptor {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTokenAuthService sysTokenAuthService;

    public BaseAuthInterceptorImpl() {
        super(AuthLevel.BASE_AUTH);
    }

    @Override
    public boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) throws IOException {
        // 确认request的project、source、nonce、phoneNo是否合法
        if (!sysUserService.nonceIsLegal(request.getHeader(RestHeader.X_NONCE.getName()))
                || StringUtil.isNullOrEmpty(request.getHeader(RestHeader.X_UUID.getName()))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal Nonce or UUID");
            return false;
        }

        // 确认request的contentMD5是否合法
        String contentMD5 = request.getHeader(RestHeader.CONTENT_MD5.getName());
        if (!StringUtil.isNullOrEmpty(requestBody) && !MD5Util.encode(requestBody).equals(contentMD5)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal ContentMD5");
            return false;
        }

        //将project写入HTTP的缓存中
        RestProject restProject = RestProject.parse(request.getHeader(RestHeader.X_PROJECT.getName()).toLowerCase());
        if (restProject == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal Project");
            return false;
        }
        request.setAttribute(RestConstants.PROJECT, restProject);

        //将RestSignHandler写入HTTP的缓存中
        String sourceName = request.getHeader(RestHeader.X_SOURCE.getName()).toLowerCase();
        RestSource restSource = RestSource.parse(restProject, sourceName);
        if (restSource == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal Source");
            return false;
        }
        request.setAttribute(RestConstants.SOURCE, restSource);

        return true;
    }
}
