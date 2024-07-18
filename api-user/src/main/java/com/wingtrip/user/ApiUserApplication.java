package com.wingtrip.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wingtrip.user")
public class ApiUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiUserApplication.class, args);
    }
}
