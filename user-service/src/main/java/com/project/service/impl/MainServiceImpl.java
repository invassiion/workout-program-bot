package com.project.service.impl;

import com.project.dao.AppUserDAO;
import com.project.entity.AppUser;
import com.project.entity.RawData;
import com.project.entity.enums.UserState;
import com.project.model.RawDataDAO;
import com.project.service.MainService;
import com.project.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.project.service.enums.ServiceCommands.*;

@Log4j
@Service
public class MainServiceImpl implements MainService {


    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;

    public MainServiceImpl(RawDataDAO userRepository, ProducerService producerService, AppUserDAO appUserDAO) {
        this.rawDataDAO = userRepository;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void processTextMessage(Update update) {
        saveUserData(update);
        var appUser = findOrSaveAppUser(update);
        var text = update.getMessage().getText();

        var output = "";

        if (CANCEL.equals(text)) {
            output = cancelProcess(appUser);
        } else if (text.startsWith("/")) {
            output = processServiceCommands(appUser, text);
        } else {
            log.error("Unknown user message: " + text);
            output = "Я не смог обработать такое сообщение:(( \n " +
                    "Введите /cancel  и попробуйте снова!";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.producerAnswer(sendMessage);
    }

    private String processServiceCommands(AppUser appUser, String cmd) {
        if (START.equals(cmd)) {
            return "Здравствуйте! Вас приветсвует бот-тренер. После небольшого опроса" +
                    "я подберу для вас индивидуальную программу тренировок. \n" +
                    "Также вы сможете отслеживать прогресс своих тренировок. Для того чтобы отметить тренировку" +
                    "введите команду /Progress и следуйте дальнейшим инструкциям." +
                    "Чтобы ознакомиться с полным списком команд введите /help";
        } else if (HELP.equals(cmd)) {
            return help();
        } else if (PROGRAM.equals(cmd)) {
            return "К сожалению данная функция еще в разработке...";
        } else if (PROGRESS.equals(cmd)) {
            return "К сожалению данная функция еще в разработке...";
        } else {
            return "Неизвестная команда! Чтобы посмотреть список доступных команд введите /help";

        }

    }

    private String help() {
        return "Список доступных команд \n" +
                "/cancel - отмена выполнения текущей команды; \n" +
                "/programm - ваша программа тренировок \n" +
                "/progress - ваш прогресс тренировок \n ";
    }

    private String cancelProcess(AppUser appUser) {
        appUserDAO.save(appUser);
        return "Команда отменена!";
    }

    private AppUser findOrSaveAppUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
        if (persistentAppUser == null) {
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .isTraining(false)
                    .state(UserState.BASIC_STATE)
                    .progress("MOCK")
                    .build();
            return appUserDAO.save(transientAppUser);
        }
        return persistentAppUser;
    }

    private void saveUserData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }
}
