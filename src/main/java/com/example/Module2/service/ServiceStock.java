package com.example.Module2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class ServiceStock {

	private static final String YAHOO_FINANCE_API_URL = "https://yahoo-finance127.p.rapidapi.com";
	private static final String xKey = "7e59f69744mshaa527e44b79a29fp1c6d42jsna899af6f420e";
	private static final String xHost = "yahoo-finance127.p.rapidapi.com";

	private final RestTemplate restTemplate;
	private final AuthService authService;

	@Autowired
	public ServiceStock(RestTemplate restTemplate, AuthService authService) {
		this.restTemplate = restTemplate;
		this.authService = authService;
	}

	public Object getAllData(String stock, String jwtToken) {
		try {
			// Authenticate and authorize the user
			authService.authenticateAndAuthorize(jwtToken);

			// Send a request to the Yahoo Finance API to retrieve stock quotes
			HttpHeaders yahooFinanceApiHeaders = new HttpHeaders();
			yahooFinanceApiHeaders.set("X-RapidAPI-Key", xKey);
			yahooFinanceApiHeaders.set("X-RapidAPI-Host", xHost);
			String apiUrl = YAHOO_FINANCE_API_URL + "/multi-quote/" + stock;
			ResponseEntity<String> response = restTemplate.exchange(
					apiUrl, HttpMethod.GET, new HttpEntity<>(yahooFinanceApiHeaders), String.class);

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			return root;
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Access");
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while calling API");
		}
	}
}