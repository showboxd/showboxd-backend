package com.app.showboxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShowboxdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowboxdApplication.class, args);
	}

}
