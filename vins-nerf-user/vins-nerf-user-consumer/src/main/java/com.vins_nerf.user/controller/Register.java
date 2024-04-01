package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.enums.DySmsTemplate;
import com.vins_nerf.core.http.*;
import com.vins_nerf.user.param.RegisterParam;
import com.vins_nerf.user.pojo.SysUser;
import com.vins_nerf.user.service.SysTokenAuthService;
import com.vins_nerf.user.service.SysUserService;
import com.vins_nerf.util.servie.DySMSService;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Register {
    private static final String URI = "/user/register";

    @DubboReference(version = "1.0.0")
    private DySMSService dySMSService;

    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    @DubboReference(version = "1.0.0")
    private SysTokenAuthService sysTokenAuthService;

    /**
     * 用户注册
     *
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @param source        请求主体的来源
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.DEFAULT_AUTH)
    public RestResponse register(@RequestBody @Valid RegisterParam param, BindingResult bindingResult,
                                 @RequestAttribute(RestConstants.PROJECT) RestProject project,
                                 @RequestAttribute(RestConstants.SOURCE) RestSource source) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        // 校验验证码。如果校验未通过，则返回验证码无效或过期错误；【CODE_IS_INVALID_OR_OVERDUE(19002)】
        RestResponse checkSmsCodeResult = dySMSService.checkSmsCode(URI, project, DySmsTemplate.REGISTER,
                param.getPhone(), param.getCode());
        if (RestResponse.isFail(checkSmsCodeResult)) return checkSmsCodeResult;

        //用户注册：将用户信息写入SysUser、SysUserInfo两个表中；
        SysUser sysUser = new SysUser();
        sysUser.setPhone(param.getPhone());
        sysUser.setEmail(param.getEmail());
        sysUser.setPassword(param.getPassword());
        RestResponse restResponse = sysUserService.register(URI, project, sysUser);
        if (RestResponse.isFail(restResponse)) return restResponse;

        //获取用户的TokenAuth
        return sysTokenAuthService.setTokenAuth(URI, source, (SysUser) restResponse.getData());
    }
}
