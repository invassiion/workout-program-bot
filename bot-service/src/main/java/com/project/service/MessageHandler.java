package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.messageUtils.MessageUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class MessageHandler {

    private final RabbitTemplate rabbitTemplate;
    private final MessageUtils messageUtils;

    @Autowired
    public MessageHandler(RabbitTemplate rabbitTemplate, MessageUtils messageUtils) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageUtils = messageUtils;

    }

    public void handleTextMessage(String username, Long chatId) {
        String message = String.format("{\"username\": \"%s\", \"chatId\": %d}", username, chatId);

        rabbitTemplate.convertAndSend("userExchange", "user.registration");
    }

//    public void handleCommand(String command, Long chatId){
//        switch (command) {
//            case "/start":
//                messageUtils.generateSendMessageWithText();
//                break;
//            case "/help":
//                messageUtils.sendMessage(chatId, "Команды: /start, /help");
//                break;
//            default:
//                messageUtils.sendMessage(chatId, "Команда не распознана.");
//                break;
//        }
    }

