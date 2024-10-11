package com.project.service.impl;

import com.project.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Log4j
@Service
public class ProducerServiceImpl implements ProducerService {

    private final RestTemplate restTemplate;

    @Value("${bot-service.url}")
    private String botServiceUrl;

    public ProducerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void producerAnswer(SendMessage sendMessage) {
        String url = botServiceUrl + "/update";
        try {
            restTemplate.postForEntity(url, sendMessage, String.class);
            log.info("Message sent to bot-service: " + sendMessage.getText());
        } catch ( Exception e) {
            log.error("Failed to send message to bot-service" , e);
        }
    }
}
