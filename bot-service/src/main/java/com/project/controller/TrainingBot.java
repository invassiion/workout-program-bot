package com.project.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j
@Component
public class TrainingBot  extends TelegramLongPollingBot {


    private final String botUsername;
    private final String botToken;


    @Autowired
    public TrainingBot(@Value("${telegram.bot.username}") String botUsername,
                       @Value("${telegram.bot.token}") String botToken,
                       DefaultBotOptions options) {
        super(options, botToken);
        this.botUsername = botUsername;
        this.botToken = botToken;

    }



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();


        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void sendAnswerMessage(SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}
