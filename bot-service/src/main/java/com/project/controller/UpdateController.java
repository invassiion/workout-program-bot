package com.project.controller;

import com.project.service.UpdateProducer;
import com.project.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static configuration.RabbitQueue.TEXT_QUEUE;

@Log4j
@Component
public class UpdateController {
    private TrainingBot trainingBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TrainingBot trainingBot) {
        this.trainingBot = trainingBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            return;
        }

        if (update.hasMessage()) {
            distributeMessage(update);
        } else {
            log.error("Unsupported message type is received" + update);
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
        updateProducer.produce(TEXT_QUEUE, update);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        trainingBot.sendAnswerMessage(sendMessage);
    }

}
