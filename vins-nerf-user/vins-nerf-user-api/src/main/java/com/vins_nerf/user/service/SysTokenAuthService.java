package com.vins_nerf.user.service;

import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.http.RestSource;
import com.vins_nerf.user.pojo.SysUser;

import java.util.Map;

public interface SysTokenAuthService {
    /**
     * 将 SysTokenAuth 写入Redis
     *
     * @param uri     调用请求的URI
     * @param source  用户来源
     * @param sysUser 用户信息
     * @return RestResponse
     */
    RestResponse setTokenAuth(String uri, RestSource source, SysUser sysUser);

    /**
     * 从 Redis 获取 SysTokenAuth
     *
     * @param source    用户来源
     * @param accesskey 用户AK
     * @return Map<String, String>
     */
    Map<String, String> getTokenAuth(RestSource source, String accesskey);

    /**
     * 从 Redis 删除 source 来源下的 SysTokenAuth
     *
     * @param uri       调用请求的URI
     * @param source    用户来源
     * @param accesskey 用户AK
     * @return RestResponse
     */
    RestResponse delTokenAuth(String uri, RestSource source, String accesskey);

    /**
     * 从 Redis 删除 project 下所有来源的TokenAuth
     *
     * @param uri       调用请求的URI
     * @param project   用户来源项目
     * @param accesskey 用户AK
     * @return RestResponse
     */
    RestResponse delAllTokenAuth(String uri, RestProject project, String accesskey);

    /**
     * 加密
     *
     * @param data 要加密的数据
     * @param key  加密key
     * @param iv   加密iv
     * @return 加密的结果
     */
    String encrypt(String data, String key, String iv);

    /**
     * 解密
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果
     */
    String decrypt(String data, String key, String iv);

    /**
     * 默认加密
     *
     * @param data 要加密的数据
     * @return 加密的结果
     */
    String defaultEncrypt(String data);

    /**
     * 默认解密
     *
     * @param data 要解密的数据
     * @return 解密的结果
     */
    String defaultDecrypt(String data);
}
