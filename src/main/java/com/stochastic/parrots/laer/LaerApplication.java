package com.stochastic.parrots.laer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@RestController
public class LaerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaerApplication.class, args);
	}

	@GetMapping("health")
	@ResponseStatus(HttpStatus.OK)
	public void health() {}
}
