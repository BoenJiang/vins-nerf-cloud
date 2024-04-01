package com.vins_nerf.user.param;

import com.vins_nerf.core.valid.DySMSCodeFormat;
import com.vins_nerf.core.valid.RestPasswordFormat;
import com.vins_nerf.core.valid.RestPhoneFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ForgetPasswordParam {
    /**
     * 手机号
     */
    @RestPhoneFormat
    private String phone;

    /**
     * 短信验证码
     */
    @DySMSCodeFormat
    private String code;

    /**
     * 新的密码
     */
    @RestPasswordFormat
    private String password;
}
