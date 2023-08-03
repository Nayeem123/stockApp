package com.example.Module2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class AuthService {

    private static final String authMicroserviceUrl = "http://localhost:8082";

    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void authenticateAndAuthorize(String jwtToken) {
        try {
            // Send a request to the authentication microservice to validate the access token
            HttpHeaders authHeaders = new HttpHeaders();
            authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
            HttpEntity<Void> authRequest = new HttpEntity<>(authHeaders);
            ResponseEntity<Boolean> authResponse = restTemplate.exchange(
                    authMicroserviceUrl + "/api/validateToken", HttpMethod.GET, authRequest, Boolean.class);

            System.out.println(authResponse);

            // Check if the access token is valid
            if (!authResponse.getBody()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid access token");
            }

            // Implement your authorization logic here
            // You can check if the authenticated user has access to the requested stock
            boolean isAuthenticated = authResponse.getBody();
            if (!isAuthenticated) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
            }

            // Additional authorization rules can be implemented based on your requirements
        } catch (Exception e) {
            log.error("Exception while calling API",e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while calling API", e);
        }

    }
}