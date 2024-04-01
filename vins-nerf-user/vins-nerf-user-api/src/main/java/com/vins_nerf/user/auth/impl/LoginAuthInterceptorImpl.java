package com.vins_nerf.user.auth.impl;

import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.*;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.pojo.SysUser;
import com.vins_nerf.user.service.SysTokenAuthService;
import com.vins_nerf.user.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

;

@Slf4j
@Component("loginAuthInterceptor")
public class LoginAuthInterceptorImpl extends AuthInterceptorImpl implements AuthInterceptor {

    @Value("${aimed.default.auth.iv}")
    private String defaultAuthIV;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTokenAuthService sysTokenAuthService;

    @Resource
    private AuthInterceptor baseAuthInterceptor;

    public LoginAuthInterceptorImpl() {
        super(AuthLevel.LOGIN_AUTH);
    }

    @Override
    public boolean authCheck(HttpServletRequest request, String requestBody, HttpServletResponse response) throws IOException {
        if (!baseAuthInterceptor.authCheck(request, requestBody, response)) return false;

        RestProject project = (RestProject) request.getAttribute(RestConstants.PROJECT);
        String username = request.getHeader(RestHeader.X_ACCESSKEY.getName());
        if (StringUtil.haveNullOrEmpty(username)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("Fail to get Http Header: " +
                    "x-Aimed-AccessKey[%s]. x-Aimed-Project[%s]", username, project));
            return false;
        }

        // 通过username，获取用户信息；
        SysUser sysUser = sysUserService.getByProjectAndUsername(project, username);
        if (sysUser == null) {
            // 如果"username"找不到对应的用户，返回401错误；
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("Fail to get SysUser " +
                    "by username[%s] and project[%s].", username, project.getName()));
            return false;
        }

        if (!signatureIsLegal(request, requestBody, sysUser.getPassword(), defaultAuthIV)) {
            // 校验signature；
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error Password.");
            return false;
        }

        RestSource restSource = (RestSource) request.getAttribute(RestConstants.SOURCE);
        RestResponse tokenAuthResponse = sysTokenAuthService.setTokenAuth(request.getRequestURI(), restSource, sysUser);
        if (RestResponse.isFail(tokenAuthResponse)) {
            // 将token写入失败
            response.sendError(tokenAuthResponse.getCode(), tokenAuthResponse.getMessage());
            return false;
        }
        request.setAttribute(RestConstants.TOKEN_AUTH, tokenAuthResponse);
        return true;
    }
}
