package com.pantsinc.FoodReactionTracker.integration.controller;

import com.pantsinc.FoodReactionTracker.dto.AuthResponse;
import com.pantsinc.FoodReactionTracker.dto.LoginRequest;
import com.pantsinc.FoodReactionTracker.dto.SignupRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldSignUpAndLogin() {
        // signup
        String email = "test" + UUID.randomUUID() + "@email.com";
        SignupRequest signup = new SignupRequest(email, "password123");

        ResponseEntity<String> signupResponse = restTemplate.postForEntity(
                "/api/auth/signup", signup, String.class);

        assertThat(signupResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // login
        LoginRequest login = new LoginRequest(email, "password123");

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                "/api/auth/login", login, AuthResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody().getToken()).isNotBlank();
    }

    @Test
    void shouldNotAllowLoginWithBadCredentials() {
        // login
        LoginRequest login = new LoginRequest("badEmail@email.com", "password123");

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                "/api/auth/login", login, AuthResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(loginResponse.getBody()).isNull();
    }
}
