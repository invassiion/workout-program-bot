package com.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


public class RabbitQueue {
    @Value("${rabbitmq.text.queue}")
    private static String textQueueName;

    @Value("${rabbitmq.command.queue}")
    private static String commandQueueName;

    @Value("${rabbitmq.answer.queue}")
    private static  String answerQueueName;

    public String getTextQueue() {
        return textQueueName;
    }

    public String getCommandQueue() {
        return commandQueueName;
    }
    public String getAnswerQueue() {
        return answerQueueName;
    }
}
