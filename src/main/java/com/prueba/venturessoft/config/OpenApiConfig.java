package com.prueba.venturessoft.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Examen VenturesSoft",
        version = "1.0",
        description = "Documentación de Microservicios para gestión de Empleados y Monedas",
        contact = @Contact(
            name = "David Alejandro",
            email = "david@tecterra.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8081", description = "Servidor Local")
    }
)
public class OpenApiConfig {
	
	@Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .path("/api/**", new PathItem());
    }
	
	@Bean
    public OpenApiCustomizer globalResponses() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses responses = operation.getResponses();
                responses.addApiResponse("201", new ApiResponse().description("Creado"));
                responses.addApiResponse("403", new ApiResponse().description("Acceso Denegado / Token Inválido"));
                responses.addApiResponse("500", new ApiResponse().description("Error Interno del Servidor"));
            }));
        };
    }

}
