package com.vins_nerf.user.param;

import com.vins_nerf.core.valid.DySMSCodeFormat;
import com.vins_nerf.core.valid.RestEmailFormat;
import com.vins_nerf.core.valid.RestPhoneFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterParam {
    /**
     * 手机
     */
    @RestPhoneFormat
    private String phone;

    /**
     * 短信验证码
     */
    @DySMSCodeFormat
    private String code;

    /**
     * 邮箱
     */
    @RestEmailFormat
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "password can't be blank.")
    private String password;
}
