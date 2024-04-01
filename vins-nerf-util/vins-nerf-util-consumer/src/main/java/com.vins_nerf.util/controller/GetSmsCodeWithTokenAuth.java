package com.vins_nerf.util.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.enums.DySmsTemplate;
import com.vins_nerf.core.enums.DySmsType;
import com.vins_nerf.core.http.*;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.pojo.SysUser;
import com.vins_nerf.user.service.SysUserService;
import com.vins_nerf.util.param.GetSmsCodeWithTokenAuthParam;
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
public class GetSmsCodeWithTokenAuth {
    private static final String URI = "/util/get-smscode-with-token-auth";

    @DubboReference(version = "1.0.0")
    private DySMSService dySMSService;

    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    /**
     * 发送短信验证码
     *
     * @param userId        用户ID
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse getSmsCodeWithTokenAuth(@RequestAttribute(RestConstants.USER_ID) Long userId,
                                                @RequestAttribute(RestConstants.PROJECT) RestProject restProject,
                                                @RequestBody @Valid GetSmsCodeWithTokenAuthParam param,
                                                BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        DySmsTemplate dySmsTemplate = DySmsTemplate.parse(param.getTemplate(), AuthLevel.TOKEN_AUTH, DySmsType.CODE);
        if (restProject == null || dySmsTemplate == null) {
            String message = String.format("Fail to get RestProjectFormat or DySmsTemplate. RestProjectFormat[%s], " +
                    "DySmsTemplate[%s], AuthLevel[DEFAULT_AUTH]", restProject.getSmsSignName(), param.getTemplate());
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, message);
        }

        SysUser sysUser = sysUserService.getByUid(userId);
        if (sysUser == null || StringUtil.isNullOrEmpty(sysUser.getPhone())) {
            return RestResponse.fail(URI, ResponseCode.HAVE_NOT_BOUND_PHONE, String.format("Fail to get sysUser's " +
                    "phone By userId[%d]", userId));
        }
        return dySMSService.sendSmsCode(URI, restProject, dySmsTemplate, sysUser.getPhone());
    }
}
