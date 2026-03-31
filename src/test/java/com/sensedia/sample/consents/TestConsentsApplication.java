package com.sensedia.sample.consents;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestConsentsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ConsentsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
