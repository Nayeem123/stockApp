package com.example.Module2.controller;
import com.example.Module2.service.AuthService;
import com.example.Module2.service.ServiceStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockControllerTest {

    @Mock
    private ServiceStock serviceStock;

    @Mock
    private AuthService authService;

    @InjectMocks
    private StockController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallApi() {
        // Mocking necessary objects and data
        String stock = "AAPL";
        String jwtToken = "valid-token";
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Test Data");

        // Mocking the authService method
        doNothing().when(authService).authenticateAndAuthorize(jwtToken);

        // Mocking the service method
        when(serviceStock.getAllData(stock, jwtToken)).thenReturn("Test Data");

        // Calling the controller method
        ResponseEntity<?> actualResponse = controller.callApi(stock, "Bearer " + jwtToken);

        // Assertion
        assertEquals(expectedResponse, actualResponse);
    }
}