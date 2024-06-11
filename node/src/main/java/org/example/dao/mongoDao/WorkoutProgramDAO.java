package org.example.dao.mongoDao;

import org.example.entity.mongoEntity.WorkoutProgram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkoutProgramDAO extends MongoRepository<WorkoutProgram, String> {
}
