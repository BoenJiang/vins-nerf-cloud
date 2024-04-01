package com.vins_nerf.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDubbo
@SpringBootApplication
public class VinsNerfUtilProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(VinsNerfUtilProviderApplication.class, args);
    }
}
