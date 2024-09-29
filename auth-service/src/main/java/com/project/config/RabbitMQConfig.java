package com.project.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue authQueue() {
        return new Queue("authQueue", false);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("authExchange");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("auth.#");
    }
}
