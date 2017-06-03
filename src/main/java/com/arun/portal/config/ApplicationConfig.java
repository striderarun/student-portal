package com.arun.portal.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"com.arun.portal"})
@EntityScan(basePackages = "com.arun.portal.entity")
@EnableJpaRepositories(basePackages = "com.arun.portal.repository")
@EnableTransactionManagement
@SpringBootApplication
public class ApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
