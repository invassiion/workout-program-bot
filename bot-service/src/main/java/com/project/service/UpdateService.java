package com.project.service;

import com.project.controller.TrainingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    void processUpdate(Update update);
    void setView(SendMessage sendMessage);
    void registerBot(TrainingBot trainingBot);
}
