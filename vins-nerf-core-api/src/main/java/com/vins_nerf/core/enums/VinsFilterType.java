package com.vins_nerf.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum VinsFilterType {
    NULL(0),//未知类型
    TYPE1(1);//类型1

    private final int state;

    public static VinsFilterType getInstance(int state) {
        for (VinsFilterType vinsFilterType : VinsFilterType.values()) {
            if (vinsFilterType.state == state) {
                return vinsFilterType;
            }
        }

        return VinsFilterType.NULL;
    }
}
