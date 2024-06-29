package com.wingtrip.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wingtrip.account")
public class ApiAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiAccountApplication.class, args);
    }

}
