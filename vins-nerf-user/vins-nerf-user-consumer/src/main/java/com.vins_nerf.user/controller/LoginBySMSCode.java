package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.enums.DySmsTemplate;
import com.vins_nerf.core.http.*;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.param.LoginBySMSCodeParam;
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
public class LoginBySMSCode {
    private static final String URI = "/user/login-by-smscode";

    @DubboReference(version = "1.0.0")
    private DySMSService dySMSService;

    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    @DubboReference(version = "1.0.0")
    private SysTokenAuthService sysTokenAuthService;

    /**
     * 用户通过手机短信登录接口
     *
     * @param project       请求主体的项目
     * @param source        请求主体的来源
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.DEFAULT_AUTH)
    public RestResponse loginBySMSCode(@RequestAttribute(RestConstants.PROJECT) RestProject project,
                                       @RequestAttribute(RestConstants.SOURCE) RestSource source,
                                       @RequestBody @Valid LoginBySMSCodeParam param, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        // 校验验证码。如果校验未通过，则返回验证码无效或过期错误；【CODE_IS_INVALID_OR_OVERDUE(19002)】
        RestResponse checkSmsCodeResult = dySMSService.checkSmsCode(URI, project, DySmsTemplate.LOGIN,
                param.getPhone(), param.getCode());
        if (RestResponse.isFail(checkSmsCodeResult)) return checkSmsCodeResult;

        // 从数据库中，获取用户信息；
        SysUser sysUser = sysUserService.getByProjectAndUsername(project, param.getPhone());
        if (sysUser == null) {
            //如果用户不存在，则写入账号；
            SysUser registerUser = new SysUser();
            registerUser.setPassword(sysTokenAuthService.defaultEncrypt(StringUtil.getNonce(32)));
            registerUser.setPhone(param.getPhone());
            RestResponse restResponse = sysUserService.register(URI, project, registerUser);
            if (RestResponse.isFail(restResponse)) return restResponse;
            sysUser = (SysUser) restResponse.getData();
        }
        return sysTokenAuthService.setTokenAuth(URI, source, sysUser);
    }
}
