package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class MessageHandler {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void handleTextMessage(String message, Long chatId) {
        rabbitTemplate.convertAndSend("userExchange", "user.registration", buildMessage(message, chatId));
    }

    public void handleCommand(String command, Long chatId) {
        switch (command) {
            case "/start":
                // При команде "/start" спрашиваем у пользователя его имя
                rabbitTemplate.convertAndSend("userExchange", "user.registration", buildMessage("Как вас зовут?", chatId));
                break;
            case "/help":
                // Реализуйте команду /help
                break;
            case "/stats":
                // Реализуйте команду /stats
                break;
            default:
                // Обработка остальных команд
                break;
        }
    }

    private String buildMessage(String message, Long chatId) {
        // Строим строку сообщения с именем пользователя и chatId
        return String.format("{\"message\": \"%s\", \"chatId\": %d}", message, chatId);
    }
}