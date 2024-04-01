package com.vins_nerf.user.param;

import com.vins_nerf.core.valid.RestPasswordFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordParam {
    /**
     * 旧的密码
     */
    @RestPasswordFormat(message = "OldPassword is illegal.")
    private String oldPassword;

    /**
     * 新的密码
     */
    @RestPasswordFormat(message = "NewPassword is illegal.")
    private String newPassword;
}
