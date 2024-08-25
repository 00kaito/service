package com.bednarz.usmobile;

import org.springframework.boot.SpringApplication;

public class TestUsmobileApplication {

	public static void main(String[] args) {
		SpringApplication.from(UsmobileApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
