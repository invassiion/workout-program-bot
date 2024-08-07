package org.example.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.dao.mongoDao.WorkoutProgramDAO;
import org.example.entity.mongoEntity.WorkoutProgram;

import org.example.service.WorkoutProgramService;

import org.example.entity.SurveyResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Getter
@RequiredArgsConstructor
@Service
public class WorkoutProgramServiceImpl implements WorkoutProgramService {

    private final WorkoutProgramDAO repository;

    @Override
    public List<WorkoutProgram> getAllprograms() {
        return repository.findAll();
    }

    @Override
    public WorkoutProgram getProgramById(String id) {
        return repository.findById(id).orElse(null);
    }
    @Override
    public WorkoutProgram saveProgram(WorkoutProgram program) {
        return repository.save(program);
    }

    @Override
    public void deleteProgram(String id) {
        repository.deleteById(id);
    }

    @Override
    public String findProgramIdBySurveyResult(SurveyResult result) {
       return repository.findAll().stream().findFirst().map(WorkoutProgram::getId).orElse(null);
    }
}



