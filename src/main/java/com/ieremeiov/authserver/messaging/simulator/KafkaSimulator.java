package com.ieremeiov.authserver.messaging.simulator;

import com.ieremeiov.authserver.repository.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class KafkaSimulator {

    private final BlockingQueue<User> topic = new LinkedBlockingDeque<>();

    public void writeMessage(User user) {
        try {
            topic.put(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public User readMessage() {
        try {
            return topic.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
