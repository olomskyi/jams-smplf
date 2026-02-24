package com.olomsky.orders.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI ordersServiceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orders Service API")
                        .description("This is the REST API for Orders Service")
                        .version("v0.1")
                        .license(new License().name("Custom License")))
                .externalDocs(new ExternalDocumentation()
                        .description("Refer to Orders Service Documentation")
                        .url("https://orders-service-dummy.com/docs"));
    }
}
