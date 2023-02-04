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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserCache userCache;
    private final UserRegistrationProducer userRegistrationProducer;
    private final PasswordEncoder encoder;

    private final Set<String> pendingRegistrations = ConcurrentHashMap.newKeySet();


    public boolean emailAvailable(String email) {
        return !userCache.contains(email) && !pendingRegistrations.contains(email);
    }

    public void begin(UserRegistration registration) {
        log.info("Beginning registration for: {}", registration.getEmail());
        User newUser = toNewUser(registration);
        pendingRegistrations.add(registration.getEmail());
        log.info("Pending registrations: {}", pendingRegistrations.size());
        userRegistrationProducer.sendUserRegistration(newUser);
    }

    public void finalize(User newUser) {
        log.info("Finalizing registration for: {}", newUser.getEmail());
        userCache.put(newUser);
        pendingRegistrations.remove(newUser.getEmail());
        log.info("Pending registrations: {}", pendingRegistrations.size());
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
