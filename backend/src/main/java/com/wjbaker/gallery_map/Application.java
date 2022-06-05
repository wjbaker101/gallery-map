package com.wjbaker.gallery_map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:secret.properties")
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}