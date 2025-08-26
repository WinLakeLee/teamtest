package com.example.teamtest.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("http://localhost:5173")
			.allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS")
			.allowedHeaders("*")
		;
	}

}
