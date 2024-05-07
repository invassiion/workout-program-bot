package org.example.dispatcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dispatcher.controller.UpdateController;
import org.example.dispatcher.service.AnswerConsumer;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RequiredArgsConstructor
@Service
public class AnswerConsumerImpl  implements AnswerConsumer {
    private final UpdateController updateController;

    @Override
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
