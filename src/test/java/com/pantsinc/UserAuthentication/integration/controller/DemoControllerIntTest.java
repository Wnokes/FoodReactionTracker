package com.pantsinc.UserAuthentication.integration.controller;

import com.pantsinc.UserAuthentication.dto.AuthResponse;
import com.pantsinc.UserAuthentication.dto.LoginRequest;
import com.pantsinc.UserAuthentication.dto.SignupRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoControllerIntTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private String loginToken;

    @BeforeAll
    void setup() {
        String email = "test"+ UUID.randomUUID()+"@email.com";
        // signup
        SignupRequest signup = new SignupRequest(email, "password123");

        ResponseEntity<String> signupResponse = restTemplate.postForEntity(
                "/api/auth/signup", signup, String.class);

        Assertions.assertThat(signupResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // login
        LoginRequest login = new LoginRequest(email, "password123");

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                "/api/auth/login", login, AuthResponse.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Extract token
        loginToken = loginResponse.getBody().getToken();
    }

    @Test
    void unauthorizedUsersCannotAccessSecuredEndpoint() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/demo/secured", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authorizedUsersCanAccessSecuredEndpoint() {
        // Set headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/demo/secured",
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void basicUsersCannotAccessAdminEndpoint() {
        // Set headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/demo/admin/secret",
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
