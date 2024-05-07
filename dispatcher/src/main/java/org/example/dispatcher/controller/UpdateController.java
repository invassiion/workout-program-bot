package org.example.dispatcher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.dispatcher.service.UpdateProducer;
import org.example.dispatcher.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@Log4j
@RequiredArgsConstructor
@Component
public class UpdateController {

    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public void  registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.getMessage() != null) {
            processCommand(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    public void processCommand(Update update) {
        updateProducer.produce(update);
        setCommandReceivedView(update);
    }

    private void setCommandReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Сообщение получено! Обрабатывается...");
        setView(sendMessage);
    }


}
