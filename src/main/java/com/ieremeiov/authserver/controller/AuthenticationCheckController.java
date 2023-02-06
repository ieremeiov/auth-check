package com.ieremeiov.authserver.controller;

import com.ieremeiov.authserver.exceptions.InvalidTokenException;
import com.ieremeiov.authserver.model.AuthorizedUser;
import com.ieremeiov.authserver.repository.User;
import com.ieremeiov.authserver.service.JwtService;
import com.ieremeiov.authserver.service.RegistrationService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AuthenticationCheckController {

    private final RegistrationService registrations;
    private final JwtService jwt;

    @GetMapping("/checkAuth")
    private ResponseEntity<?> hasCorrectToken(@RequestParam @Validated @NotBlank String token) {

        String verifiedEmail = jwt.verify(token);

        User user = registrations.isRegistered(verifiedEmail)
                .orElseThrow(InvalidTokenException::new);

        return ResponseEntity.ok(new AuthorizedUser(user.getEmail()));
    }


}
