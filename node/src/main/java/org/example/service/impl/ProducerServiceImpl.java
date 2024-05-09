package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.service.ProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.example.model.RabbitQueue.*;

@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate rabbitTemplate;


    @Override
    public void producerAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void produceToParser(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(MESSAGE_TO_PARSING, sendMessage);
    }
    @Override
    public void produceBeforeParsing(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(BEFORE_PARSING, sendMessage);
    }
}
