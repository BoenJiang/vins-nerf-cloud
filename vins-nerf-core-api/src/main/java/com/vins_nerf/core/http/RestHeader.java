package com.vins_nerf.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum RestHeader implements Comparable<RestHeader> {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_TYPE("Content-Type"),
    X_DATE("x-myjy-date"),
    X_ACCESSKEY("x-myjy-accesskey"),
    X_PROJECT("x-myjy-project"),
    X_UUID("x-myjy-uuid"),
    X_SOURCE("x-myjy-source"),
    X_NONCE("x-myjy-nonce"),
    X_SIGNATURE("x-myjy-signature");

    public static final Map<String, RestHeader> HEADER_NAME_MAP = new TreeMap<>();

    static {
        for (RestHeader restHeader : RestHeader.values()) {
            HEADER_NAME_MAP.put(restHeader.getName(), restHeader);
        }
    }

    private final String name;
}
