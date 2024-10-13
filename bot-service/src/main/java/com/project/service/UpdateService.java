package com.project.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    void processUpdate(Update update);
    void setView(SendMessage sendMessage);
}
