package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.ResponseCode;
import com.vins_nerf.core.http.RestConstants;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.http.RestfulAPI;
import com.vins_nerf.core.utils.DateUtil;
import com.vins_nerf.user.param.ResetUserInfoParam;
import com.vins_nerf.user.pojo.SysUserInfo;
import com.vins_nerf.user.service.SysUserInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ResetUserInfo {
    private static final String URI = "/user/reset-user-info";

    @DubboReference(version = "1.0.0")
    private SysUserInfoService sysUserInfoService;

    /**
     * 重置用户信息（ResetUserInfo）
     * <p>
     *
     * @param userId        SysUser对应的用户编号
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse resetUserInfo(@RequestAttribute(RestConstants.USER_ID) Long userId,
                                      @RequestBody @Valid ResetUserInfoParam param, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        SysUserInfo sysUserInfo = sysUserInfoService.getByUid(userId);
        sysUserInfo.setUserId(userId);
        sysUserInfo.setNickname(param.getNickname());
        sysUserInfo.setHeadUrl(param.getHeadUrl());
        sysUserInfo.setCountry(param.getCountry());
        sysUserInfo.setProvince(param.getProvince());
        sysUserInfo.setCity(param.getCity());
        sysUserInfo.setAddress(param.getAddress());
        sysUserInfo.setIdNo(param.getIdNo());
        sysUserInfo.setBirthday(DateUtil.parse(param.getBirthday(), DateUtil.DEFAULT_DATE_FORMAT));
        sysUserInfo.setGender(param.getGender());
        if (sysUserInfoService.update(sysUserInfo)) return RestResponse.success();
        return RestResponse.fail(URI, ResponseCode.INTERNAL_SERVER_ERROR, "Fail to update SysUserInfo.");
    }
}
