package com.wingtrip.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients("com.wingtrip.user.client")
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.wingtrip.account")
public class ApiAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiAccountApplication.class, args);
    }
}
