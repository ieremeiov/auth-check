package com.ieremeiov.authserver.controller;

import com.ieremeiov.authserver.model.UserRegistration;
import com.ieremeiov.authserver.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping("/register")
    private ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistration registration) {

        if (service.userExists(registration.getEmail())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        service.register(registration);
        log.info("Registration successful for user {}", registration.getEmail());
        return ResponseEntity.ok("You have been registered successfully");
    }

}
