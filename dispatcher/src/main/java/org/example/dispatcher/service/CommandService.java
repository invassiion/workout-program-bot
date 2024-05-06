package org.example.dispatcher.service;

import lombok.RequiredArgsConstructor;
import org.example.dispatcher.bot.BotService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@RequiredArgsConstructor
@Service
public class CommandService {

    private final BotService botService;

    public void processCommand (String command, Long chatId) {
        String response = generateResponse(command);
        botService.sendAnswerMessage(chatId, response);
    }

    private String generateResponse(String command) {
        switch (command) {
            case "/start":
                return "Привет! Добро пожаловать в нашего бота!";
            default:
                return "Неизвестная команда. Поробуйте /start для начала работы с ботом.";
        }
    }

}
