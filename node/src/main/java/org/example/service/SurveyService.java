package org.example.service;

import org.example.entity.AppUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface SurveyService {
    void startSurvey(Long userId);
   SendMessage processSurveyResponse(Update update);
}
