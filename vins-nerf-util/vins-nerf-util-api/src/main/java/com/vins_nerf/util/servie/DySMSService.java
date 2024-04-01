package com.vins_nerf.util.servie;

import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.enums.DySmsTemplate;

public interface DySMSService {
    /**
     * 单次发送短信验证码
     *
     * @param restProject   短信签名信息
     * @param dySmsTemplate 短信验证码
     * @param phone         药物分类请求信息
     * @return
     */
    RestResponse sendSmsCode(String uri, RestProject restProject, DySmsTemplate dySmsTemplate, String phone);

    /**
     * 单次发送短信验证码
     *
     * @param restProject   短信签名信息
     * @param dySmsTemplate 短信验证码
     * @param phone         药物分类请求信息
     * @param code          手机验证码
     * @return
     */
    RestResponse sendSmsCode(String uri, RestProject restProject, DySmsTemplate dySmsTemplate, String phone, String code);


    /**
     * 校验短信验证码
     *
     * @param restProject   短信签名信息
     * @param dySmsTemplate 短信验证码
     * @param phone         药物分类请求信息
     * @param code          需要验证的验证码
     * @return
     */
    RestResponse checkSmsCode(String uri, RestProject restProject, DySmsTemplate dySmsTemplate, String phone, String code);
}
