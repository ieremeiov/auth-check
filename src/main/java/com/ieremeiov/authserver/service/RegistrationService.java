package com.ieremeiov.authserver.service;

import com.ieremeiov.authserver.model.UserRegistration;
import com.ieremeiov.authserver.repository.User;
import com.ieremeiov.authserver.repository.UserTokenDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserTokenDao repo;
    private final PasswordEncoder encoder;

    public boolean userExists(String username) {
        return repo.contains(username);
    }

    public void register(UserRegistration registration) {
        repo.createOrUpdate(toNewUser(registration));
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
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
