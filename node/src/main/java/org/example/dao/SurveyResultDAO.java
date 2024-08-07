package org.example.dao;

import org.example.entity.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResultDAO extends JpaRepository<SurveyResult,Long> {
}
