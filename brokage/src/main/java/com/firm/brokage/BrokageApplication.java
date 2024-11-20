package com.firm.brokage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.firm.brokage.model")
@EnableJpaRepositories(basePackages = "com.firm.brokage.repository")
@SpringBootApplication
public class BrokageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokageApplication.class, args);
	}

}
