package com.backend.app.repos;

import com.backend.app.domain.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByFullName(String name);

    Optional<User> findByToken(String token);
}
