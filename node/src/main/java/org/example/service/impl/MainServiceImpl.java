package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.entity.RawData;
import org.example.dao.AppUserDAO;
import org.example.dao.RawDataDAO;
import org.example.entity.AppUser;
import org.example.service.MainService;
import org.example.service.ProducerService;
import org.example.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.example.service.enums.ServiceCommands.*;

@Log4j
@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private final AppUserDAO appUserDAO;
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final ScheduleService scheduleService;
    
    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var text = update.getMessage().getText();
        var output = "";
        
        if (CANCEL.equals(text)) {
            output = cancelProcess(appUser);
        } else if (START.equals(text) || HELP.equals(text) || text.startsWith(SCHEDULE.toString())) {
            output = processServiceCommand(appUser, text);
        } 
        else {
            log.error("Unknown command: "+ text );
            output = "Неизвестная ошибка! Введите /cancel и попробуйте снова!";
        }
        
        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processScheduleMessage(SendMessage sendMessage) {
        producerService.producerAnswer(sendMessage);
        log.debug("Message is received from Node");
    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.producerAnswer(sendMessage);
    }

    private String processServiceCommand(AppUser appUser, String cmd) {
        if (HELP.equals(cmd)) {
            return help();
        } else if (START.equals(cmd)) {
            return "Приветствую! Чтобы посмотреть список доступных команд введите /help";
        } else if (cmd.startsWith(SCHEDULE.toString())) {
            return processSchedule(cmd);
        } else {
            return "Неизвестная команда! Чтобы посмотреть список доступных команд введите /help";
        }
    }

    private String processSchedule(String cmd) {
       String[] parts = cmd.split(" ");
       if (parts.length < 3) {
           return "Некорректная команда! Используйте /schedule [группа] [Дата]";
       }

       String group = parts[1];
       String date = parts[2];

      return scheduleService.getSchedule(group,date);
    }

    private String help() {
        return "Список доступных команд:\n"
                + "/cancel - отмена выполнения текущей команды;"
                + "/schedule [группа] [дата] - получить расписание на указанную дату для указанной группы.";
    }

    private String cancelProcess(AppUser appUser) {
        appUserDAO.save(appUser);
        return "Команда отменена!";
    }

    private AppUser findOrSaveAppUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        AppUser persistentAppUser = appUserDAO.findUserByTelegramUserId(telegramUser.getId());
        if (persistentAppUser == null) {
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .username(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .build();
            return  appUserDAO.save(transientAppUser);
        }
        return persistentAppUser;
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }


}
