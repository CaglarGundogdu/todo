package com.ws.todoserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
 
    @Bean
    public OpenAPI customOpenAPI() {
    	Info info = new Info();
    	
    	info.setTitle("ToDo Application API");
    	info.setDescription("Swagger Documentation for ToDo Application API");
    	info.setVersion("v1.0");

    	return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}