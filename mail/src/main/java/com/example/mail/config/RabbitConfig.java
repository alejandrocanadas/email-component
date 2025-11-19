package com.example.mail.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String NOTIFICACIONES_EXCHANGE = "notificaciones.exchange";
    public static final String NOTIFICACION_COMPRA_QUEUE = "notificacion.compra.queue";

    @Bean
    public DirectExchange notificacionesExchange() {
        return new DirectExchange(NOTIFICACIONES_EXCHANGE);
    }


    @Bean
    public Queue notificacionCompraQueue() {
        return new Queue(NOTIFICACION_COMPRA_QUEUE, true);
    }

    @Bean
    public Binding bindNotificacionCompra() {
        return BindingBuilder
            .bind(notificacionCompraQueue())
            .to(notificacionesExchange())
            .with("notificacion.compra"); 
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}