package com.p05tourmgmt.tourservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class TouristServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TouristServiceApplication.class, args);
	}

}
