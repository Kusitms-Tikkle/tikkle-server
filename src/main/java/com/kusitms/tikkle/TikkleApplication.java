package com.kusitms.tikkle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TikkleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TikkleApplication.class, args);
	}

}
