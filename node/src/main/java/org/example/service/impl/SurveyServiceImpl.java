package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.surveyUtils.model.SurveyResult;
import org.example.service.SurveyService;
import org.example.service.WorkoutProgramService;
import org.example.surveyUtils.questions.SurveyQuestions;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

    private final WorkoutProgramService workoutProgramService;
    private final Map<Long, SurveyResult> surveyResults = new HashMap<>();
    private final Map<Long, Integer> userQuestionIndex = new HashMap<>();

    @Override
    public void startSurvey(Long userId) {
        surveyResults.put(userId, new SurveyResult());
        userQuestionIndex.put(userId, 0);
    }

    @Override
    public SendMessage processSurveyResponse(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        String userResponse = update.getMessage().getText();
        SurveyResult result = surveyResults.get(userId);
        int questionIndex = userQuestionIndex.get(userId);

        switch (questionIndex) {
            case 0:
                result.setGender(userResponse);
                break;
            case 1:
                result.setHeight(Integer.parseInt(userResponse));
                break;
            case 2:
                result.setWeight(Integer.parseInt(userResponse));
                break;
            case 3:
                result.setAge(Integer.parseInt(userResponse));
                break;
            case 4:
                result.setExerciseFrequency(userResponse);
                break;
            case 5:
                result.setFitnessLevel(Integer.parseInt(userResponse));
                break;
            case 6:
                result.setGoal(userResponse);
                break;
            default:
                break;
        }
        questionIndex++;
        userQuestionIndex.put(userId, questionIndex);

        if (questionIndex < SurveyQuestions.QUESTIONS.length) {
            return createQuestionMessage(update, questionIndex);
        } else {
            surveyResults.put(userId, result);
            return finalizeSurvey(update, result);
        }
    }
    
    private SendMessage createQuestionMessage(Update update, int questionIndex) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(SurveyQuestions.QUESTIONS[questionIndex]);

        if (questionIndex == 4) {
            message.setReplyMarkup(createInlineKeyboard(SurveyQuestions.FREQUENCY_OPTIONS));
        } else if (questionIndex == 6) {
            message.setReplyMarkup(createInlineKeyboard(SurveyQuestions.GOAL_OPTIONS));
        }
        return message;
    }

    private InlineKeyboardMarkup createInlineKeyboard(String[] options) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(String option : options) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(option);
            button.setCallbackData(option);
            row.add(button);
            keyboard.add(row); 
        }
        return new InlineKeyboardMarkup(keyboard);
    }
    public EditMessageText processCallbackQuery(Update update) {
        Long userId = update.getCallbackQuery().getFrom().getId();
        String userResponse = update.getCallbackQuery().getData();
        SurveyResult result = surveyResults.get(userId);
        int questionIndex = userQuestionIndex.get(userId);
        
        switch (questionIndex) {
            case 4:
                result.setExerciseFrequency(userResponse);
                break;
            case 6:
                result.setGoal(userResponse);
                break;
            default:
                break;
        }
        
        questionIndex++;
        userQuestionIndex.put(userId, questionIndex);
        
        if (questionIndex< SurveyQuestions.QUESTIONS.length) {
            return createEditMessage(update, questionIndex);
        } else {
            surveyResults.put(userId, result);
            return finalizeSurveyAsEditMessage(update, result);
        }
    }

    private EditMessageText finalizeSurveyAsEditMessage(Update update, SurveyResult result) {
        Long userId = result.getUserId();
        String programId = workoutProgramService.findProgramIdBySurveyResult(result);
        userQuestionIndex.remove(userId);
        surveyResults.remove(userId);

        EditMessageText messageText = new EditMessageText();
        messageText.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        messageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        return messageText;
    }

    private EditMessageText createEditMessage(Update update, int questionIndex) {
        EditMessageText message = new EditMessageText();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        message.setText(SurveyQuestions.QUESTIONS[questionIndex]);
        if (questionIndex == 4) {
            message.setReplyMarkup(createInlineKeyboard(SurveyQuestions.FREQUENCY_OPTIONS));
        } else if (questionIndex == 6) {
            message.setReplyMarkup(createInlineKeyboard(SurveyQuestions.GOAL_OPTIONS));
        }

        return message;
    }


    private SendMessage finalizeSurvey(Update update, SurveyResult result) {
        Long userId = result.getUserId();
        String programId = workoutProgramService.findProgramIdBySurveyResult(result);
        userQuestionIndex.remove(userId);
        surveyResults.remove(userId);

       SendMessage message = new SendMessage();
       message.setChatId(update.getMessage().getChatId().toString());
       message.setText("Спасибо за прохождение опроса! Ваша программа тренировок: " + programId);
       return  message;
    }

}



