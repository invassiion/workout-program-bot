package com.project.controller;

import com.project.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Log4j
@RestController
@RequestMapping("/update")
public class UpdateController {

    private TrainingBot trainingBot;
    private final MessageUtils messageUtils;

    public UpdateController(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    public ResponseEntity<String> receiveUpdate(@RequestBody Update update) {
        log.info("Received update from user-service: " + update);
        processUpdate(update);

        return ResponseEntity.ok("Update processed succesfully");
    }

    public void registerBot(TrainingBot trainingBot) {
        this.trainingBot = trainingBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
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
        log.debug("update has deleveried " + update);
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
