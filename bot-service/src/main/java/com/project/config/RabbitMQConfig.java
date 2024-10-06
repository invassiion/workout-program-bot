package com.project.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static configuration.RabbitQueue.ANSWER_QUEUE;
import static configuration.RabbitQueue.TEXT_QUEUE;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        return new Queue(TEXT_QUEUE,false);
    }

    @Bean
    public Queue AnswerMessageQueue() {
        return new Queue(ANSWER_QUEUE, false);
    }

}
