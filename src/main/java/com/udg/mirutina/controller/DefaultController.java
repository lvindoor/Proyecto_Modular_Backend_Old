package com.udg.mirutina.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class DefaultController {

	@GetMapping
	public ResponseEntity<?> defaultResponseEntity() {
		Map<String, Object> response = new HashMap<>();
		response.put("msg", "server is running");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}  
  
}
