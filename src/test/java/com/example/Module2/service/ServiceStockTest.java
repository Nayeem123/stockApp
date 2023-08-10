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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ServiceStockTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AuthService authService;

    private ServiceStock serviceStock;

    private static final String YAHOO_FINANCE_API_URL = "https://yahoo-finance127.p.rapidapi.com";
    private static final String xKey = "7e59f69744mshaa527e44b79a29fp1c6d42jsna899af6f420e";
    private static final String xHost = "yahoo-finance127.p.rapidapi.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceStock = new ServiceStock(restTemplate, authService);
    }

    @Test
    public void testGetAllData_ValidStock_ValidToken() throws Exception {
        // Mocking necessary objects and data
        String stock = "AAPL";
        String jwtToken = "valid-token";
        HttpHeaders yahooFinanceApiHeaders = new HttpHeaders();
        yahooFinanceApiHeaders.set("X-RapidAPI-Key", xKey);
        yahooFinanceApiHeaders.set("X-RapidAPI-Host", xHost);
        String apiUrl = YAHOO_FINANCE_API_URL+ "/multi-quote/" + stock;
        ResponseEntity<String> response = ResponseEntity.ok("{}");

        // Mocking the authService.authenticateAndAuthorize method
        doNothing().when(authService).authenticateAndAuthorize(jwtToken);

        // Mocking the restTemplate.exchange method
        when(restTemplate.exchange(
                eq(apiUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(response);

        // Calling the method under test
        Object result = serviceStock.getAllData(stock, jwtToken);


        // Assertions
        assertNotNull(result);
    }

    @Test
    public void testGetAllData_InvalidToken() {
        // Mocking necessary objects and data
        String stock = "AAPL";
        String jwtToken = "invalid-token";

        // Mocking the authService.authenticateAndAuthorize method to throw a ResponseStatusException
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(authService).authenticateAndAuthorize(jwtToken);

        // Calling the method under test
        assertThrows(ResponseStatusException.class, () -> serviceStock.getAllData(stock, jwtToken));

    }

    @Test
    public void testGetAllData_HttpServerError() {
        // Mocking necessary objects and data
        String stock = "AAPL";
        String jwtToken = "valid-token";
        HttpHeaders yahooFinanceApiHeaders = new HttpHeaders();
        yahooFinanceApiHeaders.set("X-RapidAPI-Key", xKey);
        yahooFinanceApiHeaders.set("X-RapidAPI-Host", xHost);
        String apiUrl = YAHOO_FINANCE_API_URL+ "/multi-quote/" + stock;;

        // Mocking the authService.authenticateAndAuthorize method
        doNothing().when(authService).authenticateAndAuthorize(jwtToken);

        // Mocking the restTemplate.exchange method to throw HttpServerErrorException
        when(restTemplate.exchange(
                eq(apiUrl),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // Assertion
        assertThrows(ResponseStatusException.class, () -> serviceStock.getAllData(stock, jwtToken));

    }

}
