package com.example.Module2.controller;
import com.example.Module2.model.UserStockPreference;
import com.example.Module2.service.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class KafkaControllerTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private KafkaController kafkaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPublishMessage_ValidInput() {
        // Mocking necessary objects and data
        UserStockPreference message = new UserStockPreference();
        String jwtToken = "valid-token";
        String authHeader = "Bearer " + jwtToken;

        // Calling the method under test
        String result = kafkaController.publishMessage(message, authHeader);

        // Assertion
        assertEquals("Data Published", result);
    }
}