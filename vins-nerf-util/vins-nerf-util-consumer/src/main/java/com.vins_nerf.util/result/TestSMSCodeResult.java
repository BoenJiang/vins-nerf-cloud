package com.vins_nerf.util.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestSMSCodeResult {
    /**
     * 发给用户的验证码
     */
    public String code;
}
