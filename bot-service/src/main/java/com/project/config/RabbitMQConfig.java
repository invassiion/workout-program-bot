package com.project.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.command.queue}")
    private String commandQueueName;

    @Value("${rabbitmq.answer.queue}")
    private String answerQueueName;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue commandQueue() {
        return new Queue(commandQueueName, false);
    }

    @Bean
    public Queue AnswerMessageQueue() {
        return new Queue(answerQueueName, false);
    }

}
