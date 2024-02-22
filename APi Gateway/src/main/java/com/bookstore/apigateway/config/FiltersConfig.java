package com.bookstore.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class FiltersConfig {
    @Bean
    @Order(0)
    public GlobalFilter globalFilter(){
        return (exchange, chain) -> {
            log.info("Handling Request {} " , exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Executing global post filter ........");
            }));
        };
    }
}
