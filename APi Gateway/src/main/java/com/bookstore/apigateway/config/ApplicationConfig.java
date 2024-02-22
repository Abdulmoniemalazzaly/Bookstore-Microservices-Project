package com.bookstore.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean(name = "Balanced")
    @LoadBalanced
    public WebClient.Builder webClientBuilderBalanced(){
        return WebClient.builder();
    }

    @Bean
    @Primary
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
