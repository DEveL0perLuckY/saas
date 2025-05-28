package com.backend.app.repos;

import com.backend.app.domain.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PlanRepository extends MongoRepository<Plan, Integer> {
}
