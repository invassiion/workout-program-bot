package com.project.service;

import com.project.messageUtils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Component
public class UpdateService {
    private TrainingBot trainingBot;
    private final MessageUtils messageUtils;
    private final MessageHandler messageHandler;

    public UpdateService(MessageUtils messageUtils, MessageHandler messageHandler) {
        this.messageUtils = messageUtils;
        this.messageHandler = messageHandler;
    }

    public void registerBot(TrainingBot trainingBot) {
        this.trainingBot = trainingBot;
    }

    public void processUpdate(Update update) {
        var text = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.getMessage() != null) {
            messageHandler.handleCommand(text, chatId);
        } else {
            log.error("Unsupported message type is received: " + update);
        }
    }
}
