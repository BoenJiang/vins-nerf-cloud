package com.vins_nerf.util.memdao.impl;

import com.vins_nerf.core.memdao.impl.BaseRedisDaoImpl;
import com.vins_nerf.util.memdao.DySMSRedisDao;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository(value = "dySMSRedisDao")
public class DySMSRedisDaoImpl extends BaseRedisDaoImpl implements DySMSRedisDao {
    @Value("${aimed.redis.dysms.host}")
    private String host;

    @Value("${aimed.redis.dysms.port}")
    private int port;

    @Value("${aimed.redis.dysms.username}")
    private String username;

    @Value("${aimed.redis.dysms.password}")
    private String password;

    @Value("${aimed.redis.dysms.timeout}")
    private int timeout;

    @Value("${aimed.redis.dysms.max-idle}")
    private int maxIdle;

    @Value("${aimed.redis.dysms.max-total}")
    private int maxTotal;

    @Override
    @PostConstruct
    public void init() {
        init(host, port, username, password, timeout, maxIdle, maxTotal);
    }
}
