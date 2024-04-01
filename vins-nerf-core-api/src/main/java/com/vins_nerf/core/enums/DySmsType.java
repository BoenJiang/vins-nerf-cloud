package com.vins_nerf.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum DySmsType {
    CODE, //验证码类型
    INFO; //通知类型
}
