package com.ieremeiov.authserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class UserCache {

    private final Map<String, User> userByEmail = new ConcurrentHashMap<>();

    public Optional<User> findByEmail(String email) {
        log.info("Retrieving {} from database", email);

        return userByEmail.containsKey(email) ?
                Optional.of(userByEmail.get(email)) :
                Optional.empty();
    }

    public void createOrUpdate(User newUser) {
        log.info("Create or Update user: {}", newUser);
        userByEmail.put(newUser.getEmail(), newUser);
    }

    public boolean contains(String username) {
        log.info("Checking if username {} exists", username);
        return userByEmail.containsKey(username);
    }

}
