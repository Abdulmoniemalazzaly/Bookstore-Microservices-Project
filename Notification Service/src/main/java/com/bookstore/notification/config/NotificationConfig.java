package com.bookstore.notification.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RefreshScope
@Data
public class NotificationConfig {

    private final String INTERNAL_EXCHANGE;
    private final String NOTIFICATION_QUEUE;
    private final String INTERNAL_NOTIFICATION_ROUTING_KEY;


    public NotificationConfig(@Value("${rabbitmq.exchanges.internal}") String internalExchange,
                              @Value("${rabbitmq.queues.notification}") String notificationQueue,
                              @Value("${rabbitmq.routing-keys.internal-notification}") String internalNotificationRoutingKey) {
        INTERNAL_EXCHANGE = internalExchange;
        NOTIFICATION_QUEUE = notificationQueue;
        INTERNAL_NOTIFICATION_ROUTING_KEY = internalNotificationRoutingKey;
    }

    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(INTERNAL_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue(){
        return new Queue(NOTIFICATION_QUEUE);
    }

    @Bean
    public Binding internalToNotificationBinding(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(INTERNAL_NOTIFICATION_ROUTING_KEY);
    }
}
