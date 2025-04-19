package com.example.AutoEcole;

import com.example.AutoEcole.il.config.LinkExpirationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.example.AutoEcole")
@EnableConfigurationProperties(LinkExpirationConfig.class)
public class AutoEcoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoEcoleApplication.class, args);
	}

}
