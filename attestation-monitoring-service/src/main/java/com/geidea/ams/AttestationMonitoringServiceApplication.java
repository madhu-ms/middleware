package com.geidea.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AttestationMonitoringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttestationMonitoringServiceApplication.class, args);
	}

}
