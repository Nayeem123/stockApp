package com.example.Module2.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private static final String AUTH_MICROSERVICE_URL = "http://localhost:8082";

    @Mock
    private RestTemplate restTemplate;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(restTemplate);
    }

    @Test
    void authenticateAndAuthorize_ValidToken_SuccessfulAuthentication() {
        // Arrange
        String jwtToken = "valid-token";
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        HttpEntity<Void> authRequest = new HttpEntity<>(authHeaders);
        ResponseEntity<Boolean> authResponse = ResponseEntity.ok(true);

        // Mock the dependencies
        when(restTemplate.exchange(AUTH_MICROSERVICE_URL+"/api/validateToken", HttpMethod.GET,
                authRequest, Boolean.class))
                .thenReturn(authResponse);
        ResponseEntity<Boolean> expectedAuthResponse = ResponseEntity.ok(true);

        // Assertion
        assertEquals(expectedAuthResponse,authResponse);
    }
    @Test
    void testAuthenticateAndAuthorize_InvalidToken() {
        // Arrange
        String jwtToken = "invalidToken";
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        HttpEntity<Void> authRequest = new HttpEntity<>(authHeaders);
        ResponseEntity<Boolean> authResponse = ResponseEntity.ok(false);
        when(restTemplate.exchange(
                eq(AUTH_MICROSERVICE_URL+"/api/validateToken"),
                eq(HttpMethod.GET),
                eq(authRequest),
                eq(Boolean.class)
        )).thenReturn(authResponse);

        // Assertion
        assertThrows(ResponseStatusException.class, () -> authService.authenticateAndAuthorize(jwtToken));
    }

    @Test
    public void testAuthenticateAndAuthorize_Exception() {
        // Mocking necessary objects and data
        String jwtToken = "valid-token";

        // Mocking the restTemplate.exchange method to throw HttpServerErrorException
        when(restTemplate.exchange(
                eq(AUTH_MICROSERVICE_URL+"/api/validateToken"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Boolean.class)
        )).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // Assertion
        assertThrows(ResponseStatusException.class, () -> authService.authenticateAndAuthorize(jwtToken));

    }
}