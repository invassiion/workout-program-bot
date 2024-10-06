package com.project.service.impl;

import com.project.controller.UpdateController;
import com.project.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static configuration.RabbitQueue.ANSWER_QUEUE;

@Service
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }


    @Override
    @RabbitListener(queues = ANSWER_QUEUE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
