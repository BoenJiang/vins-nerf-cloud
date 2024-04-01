package com.vins_nerf.util.param;

import com.vins_nerf.core.valid.AuthLevelFormat;
import com.vins_nerf.core.valid.DySMSTemplateFormat;
import com.vins_nerf.core.valid.RestPhoneFormat;
import com.vins_nerf.core.valid.RestProjectFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestSMSCodeParam {
    /**
     * 请求项目
     */
    @RestProjectFormat
    private String project;

    /**
     * 请求来源
     */
    @AuthLevelFormat
    private String authlevel;

    /**
     * 短信模版名称
     */
    @DySMSTemplateFormat
    private String template;

    /**
     * 短信手机号
     */
    @RestPhoneFormat
    private String phone;
}
