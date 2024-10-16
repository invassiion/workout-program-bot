package com.project.controller;

import com.project.service.UpdateService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
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
public class TrainingBot  extends  TelegramLongPollingBot{

    private final String botUsername;
    private final String botToken;
    private final UpdateService updateService;

    @Autowired
    public TrainingBot(@Value("${telegram.bot.username}") String botUsername,
                       @Value("${telegram.bot.token}") String botToken,
                       DefaultBotOptions options,
                       UpdateService updateService) {
        super(options, botToken);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.updateService = updateService;
    }


    @PostConstruct
    public void init() {
        updateService.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
       updateService.processUpdate(update);
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
