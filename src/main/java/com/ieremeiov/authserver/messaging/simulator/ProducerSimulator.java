package com.ieremeiov.authserver.messaging.simulator;

import com.ieremeiov.authserver.messaging.UserRegistrationProducer;
import com.ieremeiov.authserver.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerSimulator implements UserRegistrationProducer {

    private final KafkaSimulator kafka;

    @Override
    public void sendUserRegistration(User user) {
        kafka.writeMessage(user);
    }
}
