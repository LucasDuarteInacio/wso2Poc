package com.challenge.OrderProcessingManagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderProcessingOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor Local");

        Server dockerServer = new Server();
        dockerServer.setUrl("http://host.docker.internal:8080");
        dockerServer.setDescription("Servidor Docker");

        Contact contact = new Contact();
        contact.setEmail("suporte@orderprocessing.com");
        contact.setName("Order Processing API");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("Order Processing Management API")
                .version("1.0.0")
                .contact(contact)
                .description("API REST para gerenciamento de pedidos de e-commerce. " +
                        "Esta API fornece endpoints para gerenciar produtos, pedidos e clientes.")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, dockerServer));
    }
}

