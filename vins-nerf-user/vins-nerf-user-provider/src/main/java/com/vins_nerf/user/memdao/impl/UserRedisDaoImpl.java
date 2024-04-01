package com.vins_nerf.user.memdao.impl;

import com.vins_nerf.core.memdao.impl.BaseRedisDaoImpl;
import com.vins_nerf.user.memdao.UserRedisDao;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository(value = "userRedisDao")
public class UserRedisDaoImpl extends BaseRedisDaoImpl implements UserRedisDao {
    private static final String USER_AUTH_FORMAT = "USER_AUTH_%s_%s";//USER_AUTH_${source}_${accesskey}
    private static final String USER_NONCE_FORMAT = "USER_NONCE_%s";//USER_AUTH_${nonce}
    private static final long TOKEN_AUTH_EXPIRE_TIME = 15 * 24 * 3600L;//15天
    private static final long USER_NONCE_EXPIRE_TIME = 15 * 60L;//15分钟

    @Value("${aimed.redis.user.host}")
    private String host;

    @Value("${aimed.redis.user.port}")
    private int port;

    @Value("${aimed.redis.user.username}")
    private String username;

    @Value("${aimed.redis.user.password}")
    private String password;

    @Value("${aimed.redis.user.timeout}")
    private int timeout;

    @Value("${aimed.redis.user.max-idle}")
    private int maxIdle;

    @Value("${aimed.redis.user.max-total}")
    private int maxTotal;

    private static String getTokenAuthKey(String source, String accesskey) {
        return String.format(USER_AUTH_FORMAT, source, accesskey);
    }

    private static String getUserNonceKey(String nonce) {
        return String.format(USER_NONCE_FORMAT, nonce);
    }

    @Override
    @PostConstruct
    public void init() {
        init(host, port, username, password, timeout, maxIdle, maxTotal);
    }

    @Override
    public boolean setTokenAuth(String source, String accesskey, Map<String, String> value) {
        return OK.equals(setHashString(getTokenAuthKey(source, accesskey), value, TOKEN_AUTH_EXPIRE_TIME));
    }

    @Override
    public Map<String, String> getTokenAuth(String source, String accesskey) {
        return this.getHashAll(getTokenAuthKey(source, accesskey));
    }

    @Override
    public long delTokenAuth(String source, String accesskey) {
        return this.delKeyString(getTokenAuthKey(source, accesskey));
    }

    @Override
    public String nonceIsAbsent(String nonce) {
        return this.getset(getUserNonceKey(nonce), "ok", USER_NONCE_EXPIRE_TIME);
    }
}
