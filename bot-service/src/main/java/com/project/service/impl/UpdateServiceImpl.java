package com.project.service.impl;

import com.project.controller.TrainingBot;
import com.project.service.UpdateService;
import com.project.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Service
public class UpdateServiceImpl implements UpdateService {

    private final MessageUtils messageUtils;
    private TrainingBot trainingBot;

    @Autowired
    public UpdateServiceImpl(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;

    }

    @Override
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.hasMessage()) {
            distributeMessage(update);
        } else {
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessage(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processTextMessage(Update update) {
        log.debug("Update has been delivered: " + update);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    @Override
    public  void registerBot(TrainingBot trainingBot) {
        this.trainingBot = trainingBot;
    }

    @Override
    public void setView(SendMessage sendMessage) {
        trainingBot.sendAnswerMessage(sendMessage);
    }
}
