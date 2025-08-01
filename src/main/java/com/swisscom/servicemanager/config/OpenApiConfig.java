package com.swisscom.servicemanager.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swisscom Service Manager API")
                        .description("API documentation for managing services in Swisscom")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Swisscom Backend Team")
                                .email("hrutviknagrale125@gmail.com")
                                .url(""))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url(""));
    }
}