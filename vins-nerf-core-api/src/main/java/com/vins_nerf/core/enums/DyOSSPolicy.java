package com.vins_nerf.core.enums;

import com.vins_nerf.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Slf4j
@Getter
@AllArgsConstructor
public enum DyOSSPolicy {
    READ_WRITE_OBJECT("oss_read_write_object",
            "{\n" +
                    "    \"Version\": \"1\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Action\": [\n" +
                    "                \"oss:GetObject\",\n" +
                    "                \"oss:PutObject\",\n" +
                    "                \"oss:ListObjects\"\n" +
                    "            ],\n" +
                    "            \"Resource\": [\n" +
                    "                \"acs:oss:*:*:$BUCKET_PATH/*\",\n" +
                    "                \"acs:oss:*:*:$BUCKET_PATH\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}", 3600L),
    READ_OBJECT("oss_read_object",
            "{\n" +
                    "    \"Version\": \"1\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Action\": [\n" +
                    "                \"oss:GetObject\",\n" +
                    "                \"oss:ListObjects\"\n" +
                    "            ],\n" +
                    "            \"Resource\": [\n" +
                    "                \"acs:oss:*:*:$BUCKET_PATH/*\",\n" +
                    "                \"acs:oss:*:*:$BUCKET_PATH\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}", 3600L);

    private static final String BUCKET_PATH_MARK = "$BUCKET_PATH";

    private final String auth;
    private final String policyFormat;
    private final Long durationSeconds;

    private static final Map<String, DyOSSPolicy> DYSTS_POLICY_MAP = new HashMap<>();

    static {
        for (DyOSSPolicy policy : DyOSSPolicy.values())
            DYSTS_POLICY_MAP.put(policy.getAuth().toLowerCase(), policy);
    }

    public static DyOSSPolicy parse(String auth) {
        if (StringUtil.isNullOrEmpty(auth)) return null;

        auth = auth.toLowerCase();
        for (DyOSSPolicy dyOSSPolicy : DyOSSPolicy.values()) {
            if (dyOSSPolicy.auth.equals(auth)) {
                return dyOSSPolicy;
            }
        }
        return null;
    }

    public String getPolicy(String bucketName, String filePath) {
        if (StringUtil.haveNullOrEmpty(bucketName, filePath)) {
            throw new IllegalArgumentException("bucketName or filePath must not be Null or Empty.");
        }

        String bucketPath = new File(String.format("%s/%s", bucketName, filePath)).getPath();
        return policyFormat.replaceAll(Matcher.quoteReplacement(BUCKET_PATH_MARK), bucketPath);
    }

    public static boolean contains(String name) {
        return DYSTS_POLICY_MAP.containsKey(name.toLowerCase());
    }
}
