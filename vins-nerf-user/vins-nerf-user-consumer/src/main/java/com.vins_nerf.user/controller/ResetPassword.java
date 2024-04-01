package com.vins_nerf.user.controller;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.http.*;
import com.vins_nerf.user.param.ResetPasswordParam;
import com.vins_nerf.user.pojo.SysUser;
import com.vins_nerf.user.service.SysTokenAuthService;
import com.vins_nerf.user.service.SysUserService;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResetPassword {
    private static final String URI = "/user/reset-password";

    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    @DubboReference(version = "1.0.0")
    private SysTokenAuthService sysTokenAuthService;

    /**
     * 重置密码（ResetPassword）：在用户账号登录后，用于重置密码，是TOKEN_AUTH；
     * <p>
     * 重置密码（ResetPassword）与忘记密码（ForgetPassword）两个接口虽然功能类似，但是还是存在区别的。
     * 其中，主要区别如下：
     * * 1. 在业务场景方面：重置密码（ResetPassword）是在用户登录以后，而忘记密码（ForgetPassword）在用户登录前；
     * * 2. 在接口鉴权方面：重置密码（ResetPassword）是TOKEN_AUTH，而忘记密码（ForgetPassword）是DEFAULT_AUTH；
     *
     * @param userId        SysUser对应的用户编号
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @return
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse resetPassword(@RequestAttribute(RestConstants.PROJECT) RestProject project,
                                      @RequestAttribute(RestConstants.SOURCE) RestSource source,
                                      @RequestAttribute(RestConstants.USER_ID) Long userId,
                                      @RequestBody @Valid ResetPasswordParam param, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        SysUser sysUser = sysUserService.getByUid(userId);
        if (sysUser == null || !param.getOldPassword().equals(sysUser.getPassword())) {
            return RestResponse.fail(URI, ResponseCode.UNAUTHORIZED, "Fail to update SysUser. " +
                    "Because of the Error Password.");
        }

        //更新密码
        RestResponse updateResult = sysUserService.updatePassword(URI, project, sysUser.getId(), param.getNewPassword());
        if (RestResponse.isFail(updateResult)) return updateResult;

        // 如果更新SysUser成功，则删除用账号项目相关的AuthToken，并返回TokenAuth信息【200】
        RestResponse delTokenAuthResult = sysTokenAuthService.delAllTokenAuth(URI, project, sysUser.getAccesskey());
        if (RestResponse.isFail(delTokenAuthResult)) return delTokenAuthResult;
        return sysTokenAuthService.setTokenAuth(URI, source, sysUser);
    }
}
