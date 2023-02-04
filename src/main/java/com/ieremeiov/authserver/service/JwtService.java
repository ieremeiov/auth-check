package com.ieremeiov.authserver.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.ieremeiov.authserver.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.util.Date;

// TODO use Strategy and add randomGenerator based on property
@Component
public class JwtService {

    // TODO read constants from props/env
    private static final long EXPIRATION_TIME_MILLIS = 30000;
    private static final String SECRET = "supersecret";

    public String generateJwt(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    public String verify(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (SignatureVerificationException | JWTDecodeException ex) {
            throw new InvalidTokenException();
        }
    }

//    private String generateRandomAuthToken() {
//        return RandomStringUtils.random(20, true, false);
//    }

}
