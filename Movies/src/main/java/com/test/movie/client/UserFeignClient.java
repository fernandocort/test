package com.test.movie.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.movie.dto.User;



@FeignClient(name = "users-service", url="localhost:8080")
public interface UserFeignClient {

	@GetMapping(path = "users-repo/search/get-by-username")
	User findByUsername(@RequestParam("username") String username);
}
