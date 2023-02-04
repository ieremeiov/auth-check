package com.ieremeiov.authserver;

import com.ieremeiov.authserver.model.AuthenticatedUser;
import com.ieremeiov.authserver.model.AuthorizedUser;
import com.ieremeiov.authserver.repository.UserCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HappyTest {

    private static final String TEST_USER = "qwqwqw";
    private static final String TEST_PASSWORD = "qwqwqw";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserCache userCache;

    @Test
    void testRegistrationFlow() {
        registerUser();

        //TODO Awaitility / mockito Answer + countdownlatch
        waitForUserView();

        String jwtToken = loginAndGetToken();
        checkAuth(jwtToken);
    }

    private void waitForUserView() {
        while (!userCache.contains(TEST_USER)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void registerUser() {
        FluxExchangeResult<String> registerResult = this.webTestClient
                .post()
                .uri("/register")
                .bodyValue(validUserAndPasswordBody())
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class);

        StepVerifier.create(registerResult.getResponseBody())
                .expectNext("You have been registered successfully")
                .verifyComplete();
    }

    private String loginAndGetToken() {
        FluxExchangeResult<AuthenticatedUser> authenticatedResult = this.webTestClient
                .post()
                .uri("/userlogin")
                .bodyValue(validUserAndPasswordBody())
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(AuthenticatedUser.class);

        AuthenticatedUser authenticatedUser = authenticatedResult.getResponseBody().blockFirst();
        assertThat(authenticatedUser).isNotNull();
        assertThat(authenticatedUser.getUsername()).isEqualTo(TEST_USER);
        assertThat(authenticatedUser.getUsername()).isNotBlank();

        return authenticatedUser.getToken();
    }

    private void checkAuth(String token) {
        FluxExchangeResult<AuthorizedUser> authorizedResponse = this.webTestClient
                .get()
                .uri(builder -> builder.path("/checkAuth").queryParam("token", token).build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(AuthorizedUser.class);

        StepVerifier.create(authorizedResponse.getResponseBody())
                .expectNextMatches(user -> user.getUsername().equals(TEST_USER))
                .verifyComplete();

    }

    private String validUserAndPasswordBody() {
        return """
                                {
                                    "email": "%s",
                                    "password": "%s"
                                }
                """.formatted(TEST_USER, TEST_PASSWORD);
    }
}
