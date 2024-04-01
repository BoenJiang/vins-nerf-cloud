package com.vins_nerf.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@AllArgsConstructor
public enum SysGender {
    EMPTY(-1, "empty-error", "空错误"),
    UNKNOWN(0, "unknown", "未知"),
    MALE(1, "male", "男"),
    FEMALE(2, "female", "女");

    private final int state;
    private final String en;
    private final String ch_zn;

    private static final Map<Integer, SysGender> SYS_USER_GENDER_MAP = new HashMap<>();

    static {
        for (SysGender sysGender : SysGender.values()) {
            SYS_USER_GENDER_MAP.put(sysGender.getState(), sysGender);
        }
    }

    public static SysGender getByState(Integer state) {
        SysGender sysGender = SYS_USER_GENDER_MAP.get(state);
        return sysGender == null ? SysGender.EMPTY : sysGender;
    }
}
