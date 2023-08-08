package com.example.Module2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Module2.service.ServiceStock;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

	private final ServiceStock service;
	@GetMapping("/data")
	public ResponseEntity<?> callApi(@RequestParam String stock , @RequestHeader("Authorization") String authorizationHeader){
		String jwtToken = authorizationHeader.substring(7);
		return ResponseEntity.ok(service.getAllData(stock , jwtToken));
	}

}
