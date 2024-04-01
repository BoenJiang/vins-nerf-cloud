package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.enums.DySmsTemplate;
import com.vins_nerf.core.http.*;
import com.vins_nerf.user.param.ForgetPasswordParam;
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
public class ForgetPassword {
    private static final String URI = "/user/forget-password";

    @DubboReference(version = "1.0.0")
    private DySMSService dySMSService;

    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    @DubboReference(version = "1.0.0")
    private SysTokenAuthService sysTokenAuthService;

    /**
     * 忘记密码（ForgetPassword）：在用户账号登录前，用于重置密码，是DEFAULT_AUTH；
     * <p>
     * 重置密码（ResetPassword）与忘记密码（ForgetPassword）两个接口虽然功能类似，但是还是存在区别的。
     * 其中，主要区别如下：
     * * 1. 在业务场景方面：重置密码（ResetPassword）是在用户登录以后，而忘记密码（ForgetPassword）在用户登录前；
     * * 2. 在接口鉴权方面：重置密码（ResetPassword）是TOKEN_AUTH，而忘记密码（ForgetPassword）是DEFAULT_AUTH；
     *
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.DEFAULT_AUTH)
    public RestResponse forgetPassword(@RequestAttribute(RestConstants.PROJECT) RestProject project,
                                       @RequestAttribute(RestConstants.SOURCE) RestSource source,
                                       @RequestBody @Valid ForgetPasswordParam param, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        RestResponse codeResult = dySMSService.checkSmsCode(URI, project, DySmsTemplate.FORGET_PASSWORD,
                param.getPhone(), param.getCode());
        if (RestResponse.isFail(codeResult)) return codeResult;

        SysUser sysUser = sysUserService.getByProjectAndUsername(project, param.getPhone());
        if (sysUser == null) {
            String message = String.format("Project[%s] Phone[%s] isn't exist.", project.getName(), param.getPhone());
            return RestResponse.fail(URI, ResponseCode.PHONE_IS_NOT_EXIST, message);
        }

        //更新密码
        RestResponse updateResult = sysUserService.updatePassword(URI, project, sysUser.getId(), param.getPassword());
        if (RestResponse.isFail(updateResult)) return updateResult;

        // 如果更新SysUser成功，则删除用账号项目相关的AuthToken，并返回TokenAuth信息【200】
        RestResponse delTokenAuthResult = sysTokenAuthService.delAllTokenAuth(URI, project, sysUser.getAccesskey());
        if (RestResponse.isFail(delTokenAuthResult)) return delTokenAuthResult;
        return sysTokenAuthService.setTokenAuth(URI, source, sysUser);
    }
}
