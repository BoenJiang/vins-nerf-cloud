package com.vins_nerf.user.auth.impl;

import com.vins_nerf.core.auth.AuthInterceptor;
import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestHeader;
import com.vins_nerf.core.http.RestfulAPI;
import com.vins_nerf.core.utils.MD5Util;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.service.SysTokenAuthService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Getter
public abstract class AuthInterceptorImpl implements AuthInterceptor {
    private final AuthLevel authLevel;
    private final Set<String> pathPatterns;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private SysTokenAuthService sysTokenAuthService;

    public AuthInterceptorImpl(AuthLevel authLevel) {
        this.authLevel = authLevel;
        this.pathPatterns = new HashSet<>();
    }

    @PostConstruct
    public void init() {
        applicationContext.getBeansWithAnnotation(RestController.class).forEach((key, value) -> {
            for (Method method : value.getClass().getMethods()) {
                RestfulAPI restfulAPI = AnnotationUtils.findAnnotation(method, RestfulAPI.class);
                if (restfulAPI == null || restfulAPI.auth() != this.authLevel) continue;
                pathPatterns.addAll(Arrays.asList(restfulAPI.value()));
            }
        });
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!getPathPatterns().contains(request.getRequestURI())) return true;

        //支持跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");

        return authCheck(request, (String) request.getAttribute(RestConstants.REQUEST_BODY), response);
    }

    public boolean signatureIsLegal(HttpServletRequest request, String requestBody, String password, String iv) {
        String headerSignature = request.getHeader(RestHeader.X_SIGNATURE.getName());
        if (StringUtil.isNullOrEmpty(headerSignature)) return false;

        StringBuilder sb = new StringBuilder(request.getMethod() + "\n" + request.getRequestURI() + "\n");
        for (String name : RestHeader.HEADER_NAME_MAP.keySet()) {
            if (RestHeader.X_SIGNATURE.getName().equals(name)) continue;

            String value = request.getHeader(name);
            if (StringUtil.isNullOrEmpty(value)) {
                log.error(String.format("request[%s] nonce[%s] Fail to get header[%s].", request.getRequestURI(),
                        request.getHeader(RestHeader.X_NONCE.getName()), name));
                return false;
            }
            sb.append(name).append(":").append(value).append("\n");
        }
        sb.append("requestBody:" + requestBody).append("\n");

        String requestMD5 = MD5Util.encode(sb.toString());
        return headerSignature.equals(MD5Util.encode(sysTokenAuthService.encrypt(requestMD5, password, iv)));
    }
}
