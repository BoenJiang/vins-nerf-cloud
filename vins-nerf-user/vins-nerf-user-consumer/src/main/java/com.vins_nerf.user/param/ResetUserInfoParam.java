package com.vins_nerf.user.param;

import com.vins_nerf.core.valid.RestDateFormat;
import com.vins_nerf.core.valid.RestGenderFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetUserInfoParam {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String headUrl;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 生日
     */
    @RestDateFormat
    private String birthday;

    /**
     * 性别.
     */
    @RestGenderFormat
    private Integer gender;
}
