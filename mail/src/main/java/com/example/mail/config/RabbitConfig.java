package com.example.mail.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    
    public static final String QUEUE = "pagos.queue";

    @Bean
    public Queue pagosResultadoQueue() {
        return new Queue(QUEUE, true);
    }
}
