package com.bookstore.book;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class},
        scanBasePackages = {"com.bookstore.book.*", "com.bookstore.commons.*"})
@EnableEurekaClient
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class , args);
    }
}
