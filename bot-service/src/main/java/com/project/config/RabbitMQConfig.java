package com.project.config;


import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static configuration.RabbitQueue.ANSWER_QUEUE;
import static configuration.RabbitQueue.TEXT_QUEUE;

@Log4j
@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        log.info("Creating textMessageQueue");
        return new Queue(TEXT_QUEUE);
    }

    @Bean
    public Queue answerMessageQueue() {
        log.info("Creating AnswerMessageQueue");
        return new Queue(ANSWER_QUEUE);
    }

}
