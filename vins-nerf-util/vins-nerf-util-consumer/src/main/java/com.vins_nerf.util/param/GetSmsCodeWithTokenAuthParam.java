package com.vins_nerf.util.param;

import com.vins_nerf.core.valid.DySMSTemplateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSmsCodeWithTokenAuthParam {
    /**
     * 短信模版名称
     */
    @DySMSTemplateFormat
    private String template;
}
