package com.vins_nerf.core.http;

import com.vins_nerf.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum RestProject {
    myjy("myjy", "简驿信息");//后期改名

    private final String name;//必须全部小写（lowerCase）；【Bug来源】Flutter的Dio包，会把HttpHeader的所有字符串小写；
    private final String smsSignName;//短信签名

    private static final Map<String, RestProject> REST_PROJECT_MAP = new HashMap<>();

    static {
        for (RestProject sign : RestProject.values())
            REST_PROJECT_MAP.put(sign.getName().toLowerCase(), sign);
    }

    public static RestProject parse(String name) {
        return StringUtil.isNullOrEmpty(name) ? null : REST_PROJECT_MAP.get(name);
    }

    public static boolean contains(String name) {
        return REST_PROJECT_MAP.containsKey(name);
    }
}
