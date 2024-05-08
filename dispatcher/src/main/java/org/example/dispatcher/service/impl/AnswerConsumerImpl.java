package org.example.dispatcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dispatcher.configuration.RabbitConfiguration;
import org.example.dispatcher.controller.UpdateController;
import org.example.dispatcher.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.example.model.RabbitQueue.ANSWER_MESSAGE;


@RequiredArgsConstructor
@Service
public class AnswerConsumerImpl  implements AnswerConsumer {

    @Autowired
    private final UpdateController updateController;


    @Override
    @RabbitListener(queues = ANSWER_MESSAGE )
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
