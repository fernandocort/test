package com.test.movie.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.test.movie.client.UserFeignClient;
import com.test.movie.dto.User;
import com.test.movie.dto.UserDTO;
import com.test.movie.utils.JwtUtil;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserRestController {

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private JwtUtil jwtUtil;

	
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody UserDTO user) {

		log.info("User request: {}", user);
		try {
			User userResponse = this.userFeignClient.findByUsername(user.getUsername());
			String jwt = "{\"token\":\""+jwtUtil.generateJwt(userResponse.getUsername())+"\"}";
			return new ResponseEntity<String>(jwt, HttpStatus.OK);
		}catch(FeignException fex) {
			String response = "{ \"message\": \"User not authorized\"}" ;
			return new ResponseEntity<String>(response, HttpStatus.FORBIDDEN);
		}
		
		
	}
}
