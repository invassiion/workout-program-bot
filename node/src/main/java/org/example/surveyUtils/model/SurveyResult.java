package org.example.surveyUtils.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResult {
    private Long userId;
    private String gender;
    private int height;
    private int weight;
    private int age;
    private String exerciseFrequency;
    private int fitnessLevel;
    private String goal;


}
