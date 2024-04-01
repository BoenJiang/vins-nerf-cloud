package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.http.RestfulAPI;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
    public static final String URI = "/user/login";

    /**
     * 用户登录接口
     *
     * @param restResponse 用户Token信息
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.LOGIN_AUTH)
    public RestResponse login(@RequestAttribute(RestConstants.TOKEN_AUTH) RestResponse restResponse) {
        return restResponse;
    }
}
