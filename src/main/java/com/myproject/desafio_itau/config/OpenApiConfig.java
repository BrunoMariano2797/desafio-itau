package com.myproject.desafio_itau.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Desafio Itaú", version = "1.0", description = "API para gerenciamento de contas e transferências bancárias."))
public class OpenApiConfig {
}
