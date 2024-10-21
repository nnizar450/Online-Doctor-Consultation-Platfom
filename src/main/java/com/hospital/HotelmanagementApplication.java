package com.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.controller")
@ComponentScan({ "com.example.userRepository", "com.service" })
public class HotelmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(HotelmanagementApplication.class, args);
	}
}
