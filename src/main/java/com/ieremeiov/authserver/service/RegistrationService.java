package com.ieremeiov.authserver.service;

import com.ieremeiov.authserver.messaging.UserRegistrationProducer;
import com.ieremeiov.authserver.model.UserRegistration;
import com.ieremeiov.authserver.repository.User;
import com.ieremeiov.authserver.repository.UserCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserCache userCache;
    private final UserRegistrationProducer userRegistrationProducer;

    private final PasswordEncoder encoder;

    public boolean userExists(String username) {
        return userCache.contains(username);
    }

    public void register(UserRegistration registration) {
        User newUser = toNewUser(registration);
//        repo.createOrUpdate(newUser);
        userRegistrationProducer.sendUserRegistration(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return userCache.findByEmail(email);
    }

    private User toNewUser(UserRegistration reg) {
        return User.with()
                .email(reg.getEmail())
                .hashedPassword(encoder.encode(reg.getPassword()))
                .build();
    }

//    // TODO not atomic update
//    public void updateToken(String email, String token) {
//        repo.setUserToken(email, token);
//        repo.findByEmail(email).ifPresent(user -> user.setToken(token));
//    }

//    public Optional<User> findByToken(String token) {
//        return repo.findEmailByToken(token).flatMap(repo::findByEmail);
//    }

//    public Optional<User> findByToken(String token) {
//        return repo.findEmailByToken(token).flatMap(repo::findByEmail);
//    }
}
