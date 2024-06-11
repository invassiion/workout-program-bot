package org.example.service;

import org.example.entity.mongoEntity.WorkoutProgram;

import java.util.List;

public interface WorkoutProgramService {
    List<WorkoutProgram> getAllprograms();
    WorkoutProgram getProgramById(String id);
    WorkoutProgram saveProgram(WorkoutProgram program);
    void deleteProgram(String id);
}
