package com.ieremeiov.authserver.messaging.simulator;

import com.ieremeiov.authserver.messaging.UserRegistrationConsumer;
import com.ieremeiov.authserver.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerSimulator implements UserRegistrationConsumer {

    private final KafkaSimulator kafka;

    @Override
    public User receiveUserRegistration() {
        return kafka.readMessage();
    }
}
