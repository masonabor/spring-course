package com.lab1.helloworldweb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API - Лабораторна робота 2")
                        .version("1.0.0")
                        .description("Документація RESTful веб-сервісу з підтримкою CRUD операцій, пагінації, фільтрації та часткового оновлення")
                        .contact(new Contact()
                                .name("КПІ ім. Ігоря Сікорського")
                                .email("student@kpi.ua"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:5045")
                                .description("Development Server")
                ));
    }
}

