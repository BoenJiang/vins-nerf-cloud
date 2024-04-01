package com.vins_nerf.core.enums;

import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@AllArgsConstructor
public enum DySmsTemplate {
    LOGIN("login", "SMS_253040113", 300, AuthLevel.DEFAULT_AUTH, DySmsType.CODE),
    REGISTER("register", "SMS_126225030", 300, AuthLevel.DEFAULT_AUTH, DySmsType.CODE),
    BIND_PHONE("bind-phone", "SMS_130911198", 300, AuthLevel.TOKEN_AUTH, DySmsType.CODE),
    RESET_PASSWORD("reset-password", "SMS_159240377", 300, AuthLevel.TOKEN_AUTH, DySmsType.CODE),
    FORGET_PASSWORD("forget-password", "SMS_159240377", 300, AuthLevel.DEFAULT_AUTH, DySmsType.CODE);

    private final String name;
    private final String smsCode;
    private final Integer seconds;//验证码的有效时间，不需要验证码设为null
    private final AuthLevel authLevel;
    private final DySmsType dySmsType;

    private static final Map<String, DySmsTemplate> DYSMS_TEMPLATE_MAP = new HashMap<>();

    static {
        for (DySmsTemplate template : DySmsTemplate.values())
            DYSMS_TEMPLATE_MAP.put(template.getName().toLowerCase(), template);
    }

    public static DySmsTemplate parse(String name, AuthLevel authLevel, DySmsType dySmsType) {
        if (StringUtil.isNullOrEmpty(name) || authLevel == null) return null;

        DySmsTemplate template = DYSMS_TEMPLATE_MAP.get(name);
        if (template == null) return null;
        if (authLevel != null && !authLevel.equals(template.authLevel)) return null;
        if (dySmsType != null && !dySmsType.equals(template.dySmsType)) return null;
        return template;
    }

    public static boolean contains(String name) {
        return DYSMS_TEMPLATE_MAP.containsKey(name);
    }
}

