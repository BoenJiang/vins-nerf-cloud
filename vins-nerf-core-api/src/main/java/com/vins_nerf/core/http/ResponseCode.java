package com.vins_nerf.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //成功
    SUCCESS(200, "Success"),

    //错误请求
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    //用户账号相关错误
    HAVE_NOT_BOUND_PHONE(18001, "Have Not Bound Phone"),
    PHONE_IS_EXIST(18002, "Phone Is Exist"),
    PHONE_IS_NOT_EXIST(18003, "Phone Is Not Exist"),
    EMAIL_IS_EXIST(18004, "Email Is Exist"),
    EMAIL_IS_NOT_EXIST(18005, "Email Is Not Exist"),

    //短信相关错误
    SENDING_TOO_FREQUENTLY(19001, "Sending Too Frequently"),
    CODE_IS_INVALID_OR_OVERDUE(19002, "Code Is Invalid Or Overdue");

    private final int code;
    private final String enMessage;
}
