package com.geidea.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class TerminalManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerminalManagementServiceApplication.class, args);
	}

}
