package com.vins_nerf.util.param;

import com.vins_nerf.core.valid.DyOSSActionFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetOSSTokenParam {
    /**
     * OSS的动作
     */
    @DyOSSActionFormat
    private String action;

    /**
     * 目标
     */
    private String filePath = "";
}
