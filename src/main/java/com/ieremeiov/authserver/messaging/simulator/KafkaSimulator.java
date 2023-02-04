package com.ieremeiov.authserver.messaging.simulator;

import com.ieremeiov.authserver.repository.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@Component
public class KafkaSimulator {

    private final BlockingQueue<User> topic = new LinkedBlockingDeque<>();

    public void writeMessage(User user) {
        try {
            topic.put(user);
        } catch (InterruptedException e) {
            log.info("Put() interrupted");
        }
    }

    public User readMessage() {
        try {
            return topic.take();
        } catch (InterruptedException e) {
            log.info("Take() interrupted");
            return null;
        }
    }
}
