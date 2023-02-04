package com.ieremeiov.authserver.repository;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "with")
public class User {

    private String email;
    private String hashedPassword;
    private String token;

}
