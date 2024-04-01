package com.vins_nerf.user.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.vins_nerf.core.http.*;
import com.vins_nerf.core.utils.AESUtil;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.memdao.UserRedisDao;
import com.vins_nerf.user.pojo.SysTokenAuth;
import com.vins_nerf.user.pojo.SysUser;
import com.vins_nerf.user.service.SysTokenAuthService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@DubboService(version = "1.0.0")
public class SysTokenAuthServiceImpl implements SysTokenAuthService {

    @Value("${aimed.default.auth.secretkey}")
    private String defaultAuthSK;

    @Value("${aimed.default.auth.iv}")
    private String defaultAuthIV;

    @Resource
    private UserRedisDao userRedisDao;

    @Override
    public RestResponse setTokenAuth(String uri, RestSource source, SysUser sysUser) {
        if (sysUser == null || sysUser.getId() == null || source == null
                || StringUtil.haveNullOrEmpty(uri, sysUser.getAccesskey()))
            return RestResponse.fail(uri, ResponseCode.BAD_REQUEST, String.format("fail to set SysTokenAuth into " +
                    "redis[1]. uri[%s], source[%s], sysUser[%s]", uri, source, sysUser));

        Map<String, String> value = new HashMap<>();
        value.put(RestConstants.SECRETKEY, StringUtil.getNonce(defaultAuthSK.length()));
        value.put(RestConstants.IV, StringUtil.getNonce(defaultAuthIV.length()));
        value.put(RestConstants.USER_ID, sysUser.getId().toString());
        if (!userRedisDao.setTokenAuth(source.getAuthTokenMark(), sysUser.getAccesskey(), value))
            return RestResponse.fail(uri, ResponseCode.INTERNAL_SERVER_ERROR, "fail to set SysTokenAuth into redis[2].");

        SysTokenAuth sysTokenAuth = new SysTokenAuth();
        sysTokenAuth.setAccesskey(sysUser.getAccesskey());
        sysTokenAuth.setSecretkey(defaultEncrypt(value.get(RestConstants.SECRETKEY)));
        sysTokenAuth.setIv(defaultEncrypt(value.get(RestConstants.IV)));
        return RestResponse.success(sysTokenAuth);
    }

    @Override
    public Map<String, String> getTokenAuth(RestSource source, String accesskey) {
        return userRedisDao.getTokenAuth(source.getAuthTokenMark(), accesskey);
    }

    @Override
    public RestResponse delTokenAuth(String uri, RestSource source, String accesskey) {
        if (StringUtil.haveNullOrEmpty(uri, accesskey))
            return RestResponse.fail(uri, ResponseCode.BAD_REQUEST, String.format("Fail to del SysTokenAuth, " +
                    "because param is NUll. uri[%s], source[%s], accesskey[%s]", uri, accesskey));

        // 无论TokenAuth是否存在，均认为是成功；
        JSONObject result = new JSONObject();
        result.put("num", userRedisDao.delTokenAuth(source.getAuthTokenMark(), accesskey));
        return RestResponse.success(result);
    }

    @Override
    public RestResponse delAllTokenAuth(String uri, RestProject project, String accesskey) {
        if (project == null || StringUtil.haveNullOrEmpty(uri, accesskey))
            return RestResponse.fail(uri, ResponseCode.BAD_REQUEST, String.format("Fail to del SysTokenAuth, " +
                    "because param is NUll. uri[%s], project[%s], accesskey[%s]", uri, project, accesskey));

        //删除在RestProject下的所有密码
        for (RestSource restSource : RestSource.getByProject(project)) {
            RestResponse delTokenAuthResult = delTokenAuth(uri, restSource, accesskey);
            if (RestResponse.isFail(delTokenAuthResult)) return delTokenAuthResult;
        }
        return RestResponse.success();
    }

    @Override
    public String encrypt(String data, String key, String iv) {
        return AESUtil.encrypt(data, key, iv);
    }

    @Override
    public String decrypt(String data, String key, String iv) {
        return AESUtil.decrypt(data, key, iv);
    }

    @Override
    public String defaultEncrypt(String data) {
        return encrypt(data, defaultAuthSK, defaultAuthIV);
    }

    @Override
    public String defaultDecrypt(String data) {
        return decrypt(data, defaultAuthSK, defaultAuthIV);
    }
}
