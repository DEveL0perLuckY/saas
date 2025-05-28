package com.backend.app.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.app.domain.User;
import com.backend.app.repos.UserRepository;


@Service
public class AuthService {

    @Autowired
    UserRepository appUserRepository;

    public Optional<User> getUserFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        String token = authHeader.substring(7);
        return appUserRepository.findByToken(token);
    }

    public Optional<User> getUserWithId(Integer Id) {
        return appUserRepository.findById(Id);
    }

    public Map<Integer, User> getUsersByIds(Set<Integer> userIds) {
        List<User> users = appUserRepository.findAllById(userIds);
        return users.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
    }
}
