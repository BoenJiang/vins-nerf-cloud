package com.vins_nerf.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@EnableDubbo
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VinsNerfUserConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(VinsNerfUserConsumerApplication.class, args);
    }
}