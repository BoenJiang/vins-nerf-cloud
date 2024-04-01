package com.vins_nerf.core.http;

import com.vins_nerf.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum RestSource {
    myjy_IOS(RestProject.myjy, "myjy-ios", "myjy-phone"),
    myjy_ANDROID(RestProject.myjy, "myjy-android", "myjy-phone"),
    myjy_WEB(RestProject.myjy, "myjy-web", "myjy-web");

    private final RestProject project;
    private final String name;//必须全部小写（lowerCase）；【Bug来源】Flutter的Dio包，会把HttpHeader的所有字符串小写；
    private final String authTokenMark;

    private static final Map<String, RestSource> REST_SOURCE_MAP = new HashMap<>();

    static {
        for (RestSource restSource : RestSource.values())
            REST_SOURCE_MAP.put(restSource.getName().toLowerCase(), restSource);
    }

    public static RestSource parse(RestProject project, String name) {
        if (StringUtil.isNullOrEmpty(name) || project == null) return null;

        RestSource restSource = REST_SOURCE_MAP.get(name);
        return restSource == null || !project.equals(restSource.project) ? null : restSource;
    }

    public static boolean contains(String name) {
        return REST_SOURCE_MAP.containsKey(name);
    }

    public static List<RestSource> getByProject(RestProject project) {
        List<RestSource> restSourceList = new ArrayList<>();
        if (project == null) return restSourceList;

        for (RestSource restSource : RestSource.values()) {
            if (restSource.project == project) {
                restSourceList.add(restSource);
            }
        }
        return restSourceList;
    }
}
