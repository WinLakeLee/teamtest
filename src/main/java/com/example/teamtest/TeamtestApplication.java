package com.example.teamtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TeamtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamtestApplication.class, args);
	}

}
