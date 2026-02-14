package com.olomsky.jams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class JamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JamsApplication.class, args);
	}
}
