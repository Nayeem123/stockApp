package com.example.Module2.service;
import com.example.Module2.model.UserStockPreference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, UserStockPreference> kafkaTemplate;

    @Mock
    private AuthService authService;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    private String topic = "send";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage_ValidInput() {
        // Mocking necessary objects and data
        UserStockPreference message = new UserStockPreference();
        String jwtToken = "valid-token";

        // Mock the AuthService.authenticateAndAuthorize method to successfully authenticate and authorize the user
        doNothing().when(authService).authenticateAndAuthorize(jwtToken);

        // Calling the method under test
        kafkaProducer.sendMessage(message, jwtToken);

    }

    @Test
    public void testSendMessage_UnauthorizedAccess() {
        // Mocking necessary objects and data
        UserStockPreference message = new UserStockPreference();
        String jwtToken = "invalid-token";

        // Mocking the authService.authenticateAndAuthorize method to throw a ResponseStatusException
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(authService).authenticateAndAuthorize(jwtToken);

        // Assert that the ResponseStatusException with UNAUTHORIZED status is thrown
        assertThrows(ResponseStatusException.class, () -> kafkaProducer.sendMessage(message, jwtToken));

    }

    @Test
    public void testSendMessage_KafkaException() {
        // Mocking necessary objects and data
        UserStockPreference message = new UserStockPreference();
        String jwtToken = "valid-token";

        // Mock the AuthService.authenticateAndAuthorize method to successfully authenticate and authorize the user
        doNothing().when(authService).authenticateAndAuthorize(jwtToken);

        // Mock the KafkaTemplate.send method to throw a KafkaException
        doThrow(new KafkaException("Exception while sending message")).when(kafkaTemplate).send(any(), any());

        // Assert that the ResponseStatusException with INTERNAL_SERVER_ERROR status is thrown
        assertThrows(ResponseStatusException.class, () -> kafkaProducer.sendMessage(message, jwtToken));
    }

    @Test
    public void testSendMessage_Exception() {
        // Mocking necessary objects and data
        UserStockPreference message = new UserStockPreference();
        String jwtToken = "valid-token";

        // Mock the AuthService.authenticateAndAuthorize method to successfully authenticate and authorize the user
        doNothing().when(authService).authenticateAndAuthorize(jwtToken);

        // Mock the KafkaTemplate.send method to throw an exception
        doThrow(new RuntimeException("Exception while calling API")).when(kafkaTemplate).send(any(), any());

        // Assert that the ResponseStatusException with INTERNAL_SERVER_ERROR status is thrown
        assertThrows(ResponseStatusException.class, () -> kafkaProducer.sendMessage(message, jwtToken));
    }
}