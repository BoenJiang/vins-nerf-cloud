package com.vins_nerf.util.servie.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.vins_nerf.core.enums.DySmsTemplate;
import com.vins_nerf.core.http.ResponseCode;
import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.util.memdao.DySMSRedisDao;
import com.vins_nerf.util.servie.DySMSService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@DubboService(version = "1.0.0")
public class DySMSServiceImpl implements DySMSService {
    private static final String DYSMS_CODE_FIELD = "code";
    private static final String DYSMS_TIMESTAMP_FIELD = "timestamp";//写入时的时间戳
    private static final long STANDARD_WAITING_TIME = 60000;//60000ms = 60s
    private static final String DYSMS_CODE_KEY = "dysms_code_%s_%s_%s";//dysms_code_${RestProject.name}_${DySmsTemplate.SmsCode}_${phone}
    private static final String DYSMS_CODE_VALUE = "dysms_value_%s_%d";//dysms_value_${code}_${timestamp}
    @Value("${aimed.dysms.product}")
    private String dySMSProduct;

    @Value("${aimed.dysms.endpoint}")
    private String dySMSEndpoint;

    @Value("${aimed.dysms.region.id}")
    private String dySMSRegionId;

    @Value("${aimed.dysms.accesskey.id}")
    private String dySMSAccesskeyId;

    @Value("${aimed.dysms.accesskey.secret}")
    private String dySMSAccesskeySecret;

    @Resource
    private DySMSRedisDao dySMSRedisDao;

    private IAcsClient client;

    private static String getDysmsCodeKey(RestProject restProject, DySmsTemplate dySmsTemplate, String phone) {
        return String.format(DYSMS_CODE_KEY, restProject.name(), dySmsTemplate.name(), phone);
    }

    private static String getDysmsCodeValue(String code, long currentTimeMillis) {
        return String.format(DYSMS_CODE_VALUE, code, currentTimeMillis);
    }

    private static Map<String, String> transfer(String dysmsCodeValue) {
        Map<String, String> result = new HashMap<>();
        if (StringUtil.isNullOrEmpty(dysmsCodeValue)) return result;

        String[] values = dysmsCodeValue.split("_");
        result.put(DYSMS_CODE_FIELD, values[2]);
        result.put(DYSMS_TIMESTAMP_FIELD, values[3]);
        return result;
    }

    private static boolean isSendingTooFrequently(Map<String, String> dysmsCodeMap) {
        // 如果 dysmsCodeMap 为空，则认为当前的验证码已经过期， 返回false（不频繁）；
        if (dysmsCodeMap == null || dysmsCodeMap.isEmpty()) return false;

        // 如果 dysmsCodeMap 中没有 DYSMS_TIMESTAMP_FIELD 字段的值，则认为验证码已经过期， 返回false（不频繁）；
        String timeMillisStr = dysmsCodeMap.get(DYSMS_TIMESTAMP_FIELD);
        if (StringUtil.isNullOrEmpty(timeMillisStr)) return false;

        try {
            // 如果 dysmsCodeMap 中存在 DYSMS_TIMESTAMP_FIELD 字段的值，且字段能够转long类型，则比较该字段与当前时间戳，
            // 如果【小于STANDARD_WAITING_TIME】则认为请求验证码过于频繁， 返回true（频繁），反之false（不频繁）；
            return System.currentTimeMillis() - Long.parseLong(timeMillisStr) < STANDARD_WAITING_TIME;
        } catch (NumberFormatException e) {
            // 如果 dysmsCodeMap 中存在 DYSMS_TIMESTAMP_FIELD 字段的值，但是字段转long类型报错，
            // 则认为请求验证码过于频繁， 返回true（频繁）；
            return true;
        }
    }

    @PostConstruct
    public void init() {
        DefaultProfile.addEndpoint(dySMSRegionId, dySMSProduct, dySMSEndpoint);
        client = new DefaultAcsClient(DefaultProfile.getProfile(dySMSRegionId, dySMSAccesskeyId, dySMSAccesskeySecret));
    }

    @Override
    public RestResponse sendSmsCode(String uri, RestProject restProject, DySmsTemplate dySmsTemplate, String phone) {
        return sendSmsCode(uri, restProject, dySmsTemplate, phone, StringUtil.getSMSCode(6));
    }

    @Override
    public RestResponse sendSmsCode(String uri, RestProject restProject, DySmsTemplate dySmsTemplate, String phone, String code) {
        if (restProject == null || dySmsTemplate == null || StringUtil.haveNullOrEmpty(phone, code)) {
            return RestResponse.fail(uri, ResponseCode.BAD_REQUEST, "Param is Null or Empty.");
        }

        // 从Redis中获取上次短信信息，并判断是否频繁发送
        String key = getDysmsCodeKey(restProject, dySmsTemplate, phone);
        String value = getDysmsCodeValue(code, System.currentTimeMillis());
        if (isSendingTooFrequently(transfer(dySMSRedisDao.getset(key, value, dySmsTemplate.getSeconds())))) {
            return RestResponse.fail(uri, ResponseCode.SENDING_TOO_FREQUENTLY, String.format(
                    "Phone[%s] is sending too frequently.", phone));
        }

        // 发送短信
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(restProject.getSmsSignName());
        request.setTemplateCode(dySmsTemplate.getSmsCode());
        JSONObject templateParam = new JSONObject();
        templateParam.put("code", code);
        request.setTemplateParam(templateParam.toJSONString());
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            return response != null && "OK".equals(response.getCode()) ? RestResponse.success() :
                    RestResponse.fail(uri, ResponseCode.BAD_REQUEST, JSON.toJSONString(response));
        } catch (ClientException e) {
            return RestResponse.fail(uri, ResponseCode.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public RestResponse checkSmsCode(String uri, RestProject restProject, DySmsTemplate dySmsTemplate, String phone, String code) {
        Map<String, String> result = transfer(dySMSRedisDao.getKeyString(getDysmsCodeKey(restProject, dySmsTemplate, phone)));
        if (result == null || result.isEmpty()) {
            return RestResponse.fail(uri, ResponseCode.CODE_IS_INVALID_OR_OVERDUE, String.format(
                    "Code[%s] may be Invalid or Overdue. ResultMap that get from Redis is Null or Empty.", code));
        }

        String preCode = result.get(DYSMS_CODE_FIELD);
        if (StringUtil.isNullOrEmpty(preCode)) {
            return RestResponse.fail(uri, ResponseCode.CODE_IS_INVALID_OR_OVERDUE, String.format(
                    "Code[%s] may be Invalid or Overdue. Code that get from Redis is Null or Empty.", code));
        } else if (!preCode.equals(code)) {
            return RestResponse.fail(uri, ResponseCode.CODE_IS_INVALID_OR_OVERDUE, String.format(
                    "Code[%s] is unequal with Code in the Redis.", code));
        }
        return RestResponse.success();
    }
}
