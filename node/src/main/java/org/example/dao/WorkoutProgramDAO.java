package org.example.dao;

import org.example.entity.mongoEntity.WorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkoutProgramDAO extends JpaRepository<WorkoutProgram, String> {
}
