package com.ieremeiov.authserver.messaging.simulator;

import com.ieremeiov.authserver.messaging.UserRegistrationConsumer;
import com.ieremeiov.authserver.repository.User;
import com.ieremeiov.authserver.service.RegistrationService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadViewUpdater {

//    private volatile boolean shouldUpdateReadView = true;

    private final UserRegistrationConsumer userRegistrationConsumer;
    private final RegistrationService registrations;
    private ScheduledExecutorService scheduler;

    @PostConstruct
    private void scheduleUpdater() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(this::updateReadView, 11, 2, TimeUnit.SECONDS);
//        while (shouldUpdateReadView) {
//        updateReadView();
//        }
    }

    private void updateReadView() {
        User newUser = userRegistrationConsumer.receiveUserRegistration();
        registrations.finalize(newUser);
    }

//    private void startUpdatingReadView() {
//        shouldUpdateReadView = true;
//    }
//
//    private void stopUpdatingReadView() {
//        shouldUpdateReadView = false;
//    }

    @PreDestroy
    private void stopUpdater() {
        scheduler.shutdownNow();
    }
}
