package com.vins_nerf.core.http;

import com.alibaba.fastjson2.JSONObject;
import com.vins_nerf.core.utils.DateUtil;
import com.vins_nerf.core.utils.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RestResponse<T> {
    public static final JSONObject NULL_VALUE = new JSONObject();

    /**
     * result code
     */
    private int code;

    /**
     * result message
     */
    private String message;

    /**
     * result
     */
    private T data;

    /**
     * result timestamp
     */
    private String timestamp;

    private RestResponse() {
        this.timestamp = DateUtil.getGMTDateTime();
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getEnMessage();
    }

    private RestResponse(T data) {
        this();
        this.data = data;
    }

    private RestResponse(String URI, ResponseCode responseCode, String message, T data) {
        this(data);
        this.code = responseCode.getCode();
        if (StringUtil.isNullOrEmpty(message)) {
            this.message = responseCode.getEnMessage();
        } else {
            this.message = responseCode.getEnMessage() + ": " + message;
            log.error(String.format("[%s] %s", URI, message));
        }
    }

    public static boolean isSuccess(RestResponse restResponse) {
        return restResponse != null && ResponseCode.SUCCESS.getCode() == restResponse.getCode();
    }

    public static boolean isFail(RestResponse restResponse) {
        return !RestResponse.isSuccess(restResponse);
    }

    public static RestResponse success() {
        return RestResponse.success(NULL_VALUE);
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(data);
    }

    public static RestResponse fail(ResponseCode responseCode) {
        return fail(null, responseCode, responseCode.getEnMessage());
    }

    public static RestResponse fail(ResponseCode responseCode, String message) {
        return new RestResponse<>(null, responseCode, message, NULL_VALUE);
    }

    public static RestResponse fail(String URI, ResponseCode responseCode) {
        return new RestResponse<>(URI, responseCode, responseCode.getEnMessage(), NULL_VALUE);
    }

    public static RestResponse fail(String URI, ResponseCode responseCode, String message) {
        return new RestResponse<>(URI, responseCode, message, NULL_VALUE);
    }
}
