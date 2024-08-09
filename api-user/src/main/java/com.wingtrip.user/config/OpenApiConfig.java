package com.wingtrip.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API-USER",
                version = "v3",
                description = "This API is for user management"
        )
)
public class OpenApiConfig {
}
