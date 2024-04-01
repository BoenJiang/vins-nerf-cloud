package com.vins_nerf.core.enums;

import com.vins_nerf.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.regex.Matcher;

@Slf4j
@Getter
@AllArgsConstructor
public enum DyOSSAction {
    ADD_HEAD_URL("add_head_url", DyOSSPolicy.READ_WRITE_OBJECT, "$ACCESSKEY/portrait"),
    RESET_HEAD_URL("reset_head_url", DyOSSPolicy.READ_WRITE_OBJECT, "$ACCESSKEY/portrait"),
    UPLOAD_COLLECTOR_PICTURE("upload_collector_picture", DyOSSPolicy.READ_WRITE_OBJECT, "$ACCESSKEY/collector/$FILE_PATH");

    private static final String FILE_PATH_MARK = "$FILE_PATH";
    private static final String ACCESSKEY_MARK = "$ACCESSKEY";

    private final String name;
    private final DyOSSPolicy policy;
    private final String pathFormat;

    public String getBucketPath(String filePath, String accesskey) {
        if (StringUtil.isNullOrEmpty(accesskey)) return null;

        filePath = StringUtil.isNullOrEmpty(filePath) ? "" : filePath;
        return new File(this.getPathFormat().replaceAll(Matcher.quoteReplacement(FILE_PATH_MARK), filePath)
                .replaceAll(Matcher.quoteReplacement(ACCESSKEY_MARK), accesskey)).getPath();
    }

    public static DyOSSAction parse(String name) {
        if (StringUtil.haveNullOrEmpty(name)) return null;

        for (DyOSSAction dyOSSAction : DyOSSAction.values()) {
            if (dyOSSAction.name.equals(name)) {
                return dyOSSAction;
            }
        }
        return null;
    }

    public static boolean contains(String name) {
        return parse(name) != null;
    }
}
