package com.vins_nerf.util.servie;

import com.vins_nerf.core.http.RestResponse;
import com.aliyuncs.auth.sts.AssumeRoleRequest;

public interface DySTSService {
    /**
     * 获取操作OSS的临时身份凭证
     *
     * @param request 获取临时身份凭证的请求
     * @return
     */
    RestResponse assumeRole(AssumeRoleRequest request);
}
