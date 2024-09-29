package com.project.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void handleTextMessage(String message) {
        rabbitTemplate.convertAndSend("authExchange", "bot.message", message);
    }

    public void handleCommand(String command) {

        switch (command) {
            case "/start":

                break;
            case "/help":

                break;
            case "/stats":
                break;
            default:
                break;
        }
    }
}
