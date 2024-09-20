package main.java.com.project.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendWorkoutNotification(String message) {
        rabbitTemplate.convertAndSend("authExchange","auth.notification",message);
    }
}
