package com.backend.app.repos;

import com.backend.app.domain.Plan;
import com.backend.app.domain.Subscription;
import com.backend.app.domain.User;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface SubscriptionRepository extends MongoRepository<Subscription, Integer> {

    Subscription findFirstByUser(User user);

    Subscription findFirstByPlan(Plan plan);
    List<Subscription> findAllByUser(User user);

}
