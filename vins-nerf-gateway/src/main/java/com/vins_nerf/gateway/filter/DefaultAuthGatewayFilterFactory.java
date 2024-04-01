package com.vins_nerf.gateway.filter;

import com.vins_nerf.gateway.config.EmptyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<EmptyConfig> {
    @Override
    public GatewayFilter apply(EmptyConfig config) {
        return (exchange, chain) -> chain.filter(exchange);
    }
}
