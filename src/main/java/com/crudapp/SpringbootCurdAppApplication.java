package com.crudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.crudapp")
@SpringBootApplication
public class SpringbootCurdAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCurdAppApplication.class, args);
	}

}
