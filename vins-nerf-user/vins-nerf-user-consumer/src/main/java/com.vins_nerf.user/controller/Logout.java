package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.http.RestSource;
import com.vins_nerf.core.http.RestfulAPI;
import com.vins_nerf.user.service.SysTokenAuthService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Logout {
    private static final String URI = "/user/logout";

    @DubboReference(version = "1.0.0")
    private SysTokenAuthService sysTokenAuthService;

    /**
     * 用户登出
     *
     * @param accesskey 用户AK
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse logout(@RequestAttribute(RestConstants.ACCESSKEY) String accesskey,
                               @RequestAttribute(RestConstants.SOURCE) RestSource source) {
        return sysTokenAuthService.delTokenAuth(URI, source, accesskey);
    }
}
