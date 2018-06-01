package com.vitja.study.degree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.vitja.study.degree")
public class DegreeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DegreeApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DegreeApplication.class);
	}
}
