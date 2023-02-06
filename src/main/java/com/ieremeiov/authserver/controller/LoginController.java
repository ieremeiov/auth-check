package com.ieremeiov.authserver.controller;

import com.ieremeiov.authserver.exceptions.InvalidCredentialsException;
import com.ieremeiov.authserver.model.AuthenticatedUser;
import com.ieremeiov.authserver.model.UserLogin;
import com.ieremeiov.authserver.service.JwtService;
import com.ieremeiov.authserver.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class LoginController {

    private final RegistrationService registrations;
    private final JwtService tokens;
    private final PasswordEncoder encoder;

    @PostMapping("/userlogin")
    private AuthenticatedUser login(@RequestBody @Valid UserLogin loginRequest) {

        validateCredentials(loginRequest);

        String generatedToken = tokens.generateJwt(loginRequest.getEmail());

        return new AuthenticatedUser(loginRequest.getEmail(), generatedToken);
    }

    private void validateCredentials(UserLogin loginRequest) {
        registrations.isRegistered(loginRequest.getEmail())
                .filter(user -> encoder.matches(loginRequest.getPassword(), user.getHashedPassword()))
                .orElseThrow(InvalidCredentialsException::new);
    }

}
