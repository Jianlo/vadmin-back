package com.jian.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jian"})
public class ServiceAdmin {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAdmin.class,args);
    }
}
