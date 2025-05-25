package com.caina.pautaservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.caina.pautaservices.repository")
@EntityScan(basePackages = "com.caina.pautaservices.model")
public class PautaservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PautaservicesApplication.class, args);
	}

}
