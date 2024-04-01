package com.vins_nerf.core.auth;

import com.vins_nerf.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum AuthLevel {
    NO_AUTH("no-auth"),//无需鉴权
    BASE_AUTH("base-auth"),//基本鉴权
    DEFAULT_AUTH("default-auth"),//默认鉴权
    TOKEN_AUTH("token-auth"),//用户令牌鉴权
    LOGIN_AUTH("login-auth"),//用户登录鉴权
    ROLE_AUTH("role-auth");//用户角色鉴权

    private String name;

    private static final Map<String, AuthLevel> AUTH_LEVEL_MAP = new HashMap<>();

    static {
        for (AuthLevel authLevel : AuthLevel.values())
            AUTH_LEVEL_MAP.put(authLevel.getName().toLowerCase(), authLevel);
    }

    public static AuthLevel parse(String name) {
        return StringUtil.isNullOrEmpty(name) ? null : AUTH_LEVEL_MAP.get(name);
    }

    public static boolean contains(String name) {
        return AUTH_LEVEL_MAP.containsKey(name);
    }
}
