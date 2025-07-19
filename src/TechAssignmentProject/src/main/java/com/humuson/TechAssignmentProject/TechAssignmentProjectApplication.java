package com.humuson.TechAssignmentProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.time.Duration;

@SpringBootApplication
public class TechAssignmentProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechAssignmentProjectApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
			.setConnectTimeout(Duration.ofSeconds(10))
			.setReadTimeout(Duration.ofSeconds(30))
			.build();
	}

}
