package org.example.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ConsumerService {
    void consumeMessageToParsing(SendMessage sendMessage);

//    void consumeBeforeParsing(SendMessage sendMessage);
}
