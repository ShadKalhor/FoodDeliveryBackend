package com.example.FoodDeliveryBackend;

import com.example.FoodDeliveryBackend.infrastructure.security.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakProperties.class)
public class FoodDeliveryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryBackendApplication.class, args);
	}

}
