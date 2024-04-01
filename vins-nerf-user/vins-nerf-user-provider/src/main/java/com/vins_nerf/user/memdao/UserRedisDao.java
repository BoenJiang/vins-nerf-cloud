package com.vins_nerf.user.memdao;

import com.vins_nerf.core.memdao.BaseRedisDao;

import java.util.Map;

public interface UserRedisDao extends BaseRedisDao {
    /**
     * 将 SysTokenAuth 写入Redis
     *
     * @param source    用户来源
     * @param accesskey 用户AK
     * @param value     用户鉴权信息
     * @return
     */
    boolean setTokenAuth(String source, String accesskey, Map<String, String> value);

    /**
     * 从 Redis 获取 SysTokenAuth
     *
     * @param source    用户来源
     * @param accesskey 用户AK
     * @return UserAuthInfo
     */
    Map<String, String> getTokenAuth(String source, String accesskey);

    /**
     * 从 Redis 删除 SysTokenAuth
     *
     * @param source    用户来源
     * @param accesskey 用户AK
     * @return
     */
    long delTokenAuth(String source, String accesskey);

    /**
     * 确认nonce是否存在
     *
     * @param nonce 请求随机码
     * @return
     */
    String nonceIsAbsent(String nonce);
}
