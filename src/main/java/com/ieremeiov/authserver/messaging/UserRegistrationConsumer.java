package com.ieremeiov.authserver.messaging;

import com.ieremeiov.authserver.repository.User;

public interface UserRegistrationConsumer {
    User receiveUserRegistration();
}
