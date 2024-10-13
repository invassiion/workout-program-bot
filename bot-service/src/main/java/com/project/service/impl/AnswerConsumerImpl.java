package com.project.service.impl;

import com.project.controller.UpdateController;
import com.project.service.AnswerConsumer;
import com.project.service.UpdateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Service
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateService updateService;

    public AnswerConsumerImpl(UpdateController updateController, UpdateService updateService) {
        this.updateService = updateService;
    }

    @Override
    public void consume(SendMessage sendMessage) {
        updateService.setView(sendMessage);
    }
}
