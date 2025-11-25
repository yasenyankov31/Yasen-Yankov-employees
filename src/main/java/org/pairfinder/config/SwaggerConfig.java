package org.pairfinder.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pair Finder API",
                version = "1.0",
                description = "API for finding longest working pairs"
        )
)
public class SwaggerConfig {
}
