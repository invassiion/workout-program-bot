package com.project.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AuthMessageListener {

    @RabbitListener(queues = "authQueue")
    public void handleMessage(String message) {
        // Логика обработки сообщения
        System.out.println("Received message: " + message);
    }
}
