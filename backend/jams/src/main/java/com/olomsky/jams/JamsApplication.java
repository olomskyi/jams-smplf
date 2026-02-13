package com.olomsky.jams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class JamsApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
            .directory("backend/jams")
            .load();
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );

		SpringApplication.run(JamsApplication.class, args);
	}
}
