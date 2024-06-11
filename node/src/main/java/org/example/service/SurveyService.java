package org.example.service;

import org.example.entity.AppUser;

public interface SurveyService {
    String startSurvey(AppUser appUser);
    String handleSurveyResponse(AppUser appUser, String response);
}
