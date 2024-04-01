package com.vins_nerf.gateway.filter;

import com.vins_nerf.core.http.RestHeader;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.gateway.config.EmptyConfig;
import com.vins_nerf.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
public class BaseAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<EmptyConfig> {
    @DubboReference(version = "1.0.0")
    private SysUserService sysUserService;

    @Override
    public GatewayFilter apply(EmptyConfig config) {
        return (exchange, chain) -> {
            // 确认request的project、source、nonce、phoneNo是否合法
            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (!sysUserService.nonceIsLegal(headers.getFirst(RestHeader.X_NONCE.getName()))
                    || StringUtil.isNullOrEmpty(headers.getFirst(RestHeader.X_UUID.getName()))) {
                exchange.getResponse().setStatusCode(BAD_REQUEST);
                exchange.getResponse().writeWith(Mono.error(new Throwable("Illegal Nonce or UUID")));
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}
