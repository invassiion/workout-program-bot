package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.entity.RawData;
import org.example.dao.AppUserDAO;
import org.example.dao.RawDataDAO;
import org.example.entity.AppUser;
import org.example.service.MainService;
import org.example.service.ProducerService;
import org.example.service.SurveyService;
import org.example.service.WorkoutProgramService;
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
    private final WorkoutProgramService workoutProgramService;
    private final SurveyService surveyService;
    
    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var text = update.getMessage().getText();
        var output = "";

        if (CANCEL.equals(text)) {
            output = cancelProcess(appUser);
        } else if (START.equals(text)) {
            output = startCommand();
        } else if (HELP.equals(text)) {
            output = help();
        } else if (SURVEY.equals(text)) {
            output = startSurvey(appUser);
        } else if (text.startsWith(WORKOUT_PROGRAM.toString())) {
            output = processWorkoutProgram(appUser, text);
        } else {
            log.error("Неизвестная команда: " + text);
            output = "Неизвестная команда! Введите /cancel и попробуйте снова!";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processProgramMessage(SendMessage sendMessage) {
        producerService.producerAnswer(sendMessage);
        log.debug("Сообщение получено от Node");
    }

    private String startSurvey(AppUser appUser) {
        return surveyService.startSurvey(appUser);
    }

    private String startCommand() {
        return "Приветствую! Чтобы посмотреть список доступных команд, введите /help";
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
            return startCommand();
        } else if (cmd.startsWith(WORKOUT_PROGRAM.toString())) {
            return processWorkoutProgram(appUser, cmd);
        } else {
            return "Неизвестная команда! Чтобы посмотреть список доступных команд, введите /help.";
        }
    }

    private String processWorkoutProgram(AppUser appUser, String cmd) {
        // Извлечение ID программы из команды и получение программы из базы данных
        String programId = cmd.substring(WORKOUT_PROGRAM.toString().length()).trim();
        return String.valueOf(workoutProgramService.getProgramById(programId));
    }

    private String help() {
        return "Список доступных команд:\n"
                + "/cancel - отмена выполнения текущей команды;\n"
                + "/program - получить программу тренировок;\n"
                + "/survey - пройти опрос для подбора программы тренировок.";
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
