package com.vins_nerf.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest rawRequest = exchange.getRequest();

        ServerHttpRequest decorator = new ServerHttpRequestDecorator(rawRequest) {
            @Override
            public Flux<DataBuffer> getBody() {
                return getDelegate().getBody();
            }

            @Override
            public HttpHeaders getHeaders() {
                return getDelegate().getHeaders();
            }

        };


        // 使用修改后的ServerHttpRequestDecorator重新生成一个新的ServerWebExchange
        return chain.filter(exchange.mutate().request(decorator).build());
    }

    @Override
    public int getOrder() {
        return 999;
    }
}
