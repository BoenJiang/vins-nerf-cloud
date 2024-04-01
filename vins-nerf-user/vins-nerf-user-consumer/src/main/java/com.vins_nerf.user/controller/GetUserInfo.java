package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.enums.SysGender;
import com.vins_nerf.core.http.ResponseCode;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.http.RestfulAPI;
import com.vins_nerf.core.utils.DateUtil;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.pojo.SysUserInfo;
import com.vins_nerf.user.result.GetUserInfoResult;
import com.vins_nerf.user.service.SysUserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetUserInfo {
    private static final String URI = "/user/get-user-info";

    @Value("${aimed.default-headUrl}")
    private String defaultHeadUrl;

    @DubboReference(version = "1.0.0")
    private SysUserInfoService sysUserInfoService;

    /**
     * 获取用户信息（GetUserInfo）
     * <p>
     *
     * @param userId SysUser对应的用户编号
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse getUserInfo(@RequestAttribute(RestConstants.USER_ID) Long userId) {
        SysUserInfo sysUserInfo = sysUserInfoService.getByUid(userId);
        if (sysUserInfo == null) {
            String message = String.format("fail to get SysUserInfo by userId[%d].", userId);
            return RestResponse.fail(URI, ResponseCode.INTERNAL_SERVER_ERROR, message);
        }

        GetUserInfoResult result = new GetUserInfoResult();
        result.setNickname(sysUserInfo.getNickname());
        result.setHeadUrl(StringUtil.isNullOrEmpty(sysUserInfo.getHeadUrl()) ? defaultHeadUrl : sysUserInfo.getHeadUrl());
        result.setCountry(sysUserInfo.getCountry());
        result.setProvince(sysUserInfo.getProvince());
        result.setCity(sysUserInfo.getCity());
        result.setAddress(sysUserInfo.getAddress());
        result.setIdNo(sysUserInfo.getIdNo());
        result.setBirthday(DateUtil.getDefaultDate(sysUserInfo.getBirthday()));
        result.setGender(SysGender.getByState(sysUserInfo.getGender()).getCh_zn());
        return RestResponse.success(result);
    }
}
