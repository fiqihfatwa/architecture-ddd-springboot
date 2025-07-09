package com.example.demo_ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DemoDddApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDddApplication.class, args);
	}

}
