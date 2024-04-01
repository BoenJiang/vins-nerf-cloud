package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.http.RestSource;
import com.vins_nerf.core.http.RestfulAPI;
import com.vins_nerf.user.service.SysTokenAuthService;
import com.vins_nerf.user.service.SysUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetAuthToken {
    private static final String URI = "/user/get-auth-token";

    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    @DubboReference(version = "1.0.0")
    private SysTokenAuthService sysTokenAuthService;

    /**
     * 更新用户AuthToken，并延长过期时间；
     * <p>
     *
     * @param userId SysUser对应的用户编号
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse getAuthToken(@RequestAttribute(RestConstants.SOURCE) RestSource source,
                                     @RequestAttribute(RestConstants.USER_ID) Long userId) {
        return sysTokenAuthService.setTokenAuth(URI, source, sysUserService.getByUid(userId));
    }
}
