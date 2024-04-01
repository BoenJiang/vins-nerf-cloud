package com.vins_nerf.util.servie.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.profile.DefaultProfile;
import com.vins_nerf.core.http.ResponseCode;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.util.servie.DySTSService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service("dySTSService")
@DubboService(version = "1.0.0")
public class DySTSServiceImpl implements DySTSService {
    @Value("${aimed.dysts.region.id}")
    private String dySTSRegionId;

    @Value("${aimed.dysts.accesskey.id}")
    private String dySTSAccesskeyId;

    @Value("${aimed.dysts.accesskey.secret}")
    private String dySTSAccesskeySecret;

    @Override
    public RestResponse assumeRole(AssumeRoleRequest request) {
        //设置调用者（RAM用户或RAM角色）的AccessKey ID和AccessKey Secret。
        DefaultProfile profile = DefaultProfile.getProfile(dySTSRegionId, dySTSAccesskeyId, dySTSAccesskeySecret);
        try {
            //构建一个阿里云客户端，发起请求，并得到响应。
            return RestResponse.success(new DefaultAcsClient(profile).getAcsResponse(request));
        } catch (Exception e) {
            return RestResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR, JSON.toJSONString(e));
        }
    }
}
