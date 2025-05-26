package com.social;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info =
@Info(title = "Documentation API", version = "v1.1", description = "Documentation API v1.0")
)
public class ApiDocumentationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDocumentationServiceApplication.class, args);
    }

}
