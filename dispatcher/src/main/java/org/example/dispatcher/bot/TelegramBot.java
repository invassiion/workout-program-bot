package org.example.dispatcher.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.dispatcher.service.CommandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@RequiredArgsConstructor
@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot implements BotService{

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

  private final CommandService commandService;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Received update: " + update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            commandService.processCommand(command, chatId);
        }
    }


    @Override
    public void sendAnswerMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }
}
