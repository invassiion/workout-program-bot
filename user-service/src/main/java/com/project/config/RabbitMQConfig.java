package com.project.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final RabbitQueue rabbitQueue;

    public RabbitMQConfig(RabbitQueue rabbitQueue) {
        this.rabbitQueue = rabbitQueue;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textQueue() {
        return new Queue(rabbitQueue.getTextQueue(), false);
    }

    @Bean
    public Queue commandQueue() {
        return new Queue(rabbitQueue.getCommandQueue(), false);
    }

    @Bean
    public Queue AnswerMessageQueue() {
        return new Queue(rabbitQueue.getAnswerQueue(), false);
    }

}