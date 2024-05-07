package org.example.dispatcher.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public interface AnswerConsumer {
    void consume(SendMessage sendMessage);
}
