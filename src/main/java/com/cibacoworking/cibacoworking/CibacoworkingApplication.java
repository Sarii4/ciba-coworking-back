package com.cibacoworking.cibacoworking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CibacoworkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CibacoworkingApplication.class, args);
		System.out.println("HolaaaAA");
	}

}
