package com.vins_nerf.util.param;

import com.vins_nerf.core.valid.DySMSTemplateFormat;
import com.vins_nerf.core.valid.RestPhoneFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSmsCodeWithDefaultAuthParam {
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
