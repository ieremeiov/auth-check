package com.ieremeiov.authserver.messaging;

import com.ieremeiov.authserver.repository.User;

public interface UserRegistrationProducer {

    void sendUserRegistration(User message);
}
