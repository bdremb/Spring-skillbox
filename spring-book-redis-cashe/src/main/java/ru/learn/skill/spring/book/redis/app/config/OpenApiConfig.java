package ru.learn.skill.spring.book.redis.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Info info = new Info()
                .title("Book API")
                .version("1.0")
                .description("Homework 5.8. Redis, Cache");

        return new OpenAPI().info(info).servers(List.of(localhostServer));
    }
}
