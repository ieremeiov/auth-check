package com.ieremeiov.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AuthCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthCheckApplication.class, args);
    }

}
