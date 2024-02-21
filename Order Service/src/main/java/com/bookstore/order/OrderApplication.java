package com.bookstore.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class},
        scanBasePackages = {"com.bookstore.order.*" , "com.bookstore.commons.*" , "com.bookstore.clients.config"})
@EnableEurekaClient
@EntityScan(basePackages = {"com.bookstore.order.model" , "com.bookstore.commons.model"})
@EnableFeignClients(
        basePackages = "com.bookstore.clients.payment"
)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args);
    }
}
