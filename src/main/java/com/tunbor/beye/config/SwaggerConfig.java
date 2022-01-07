package com.tunbor.beye.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olakunle Awotunbo
 */
@Configuration
public class SwaggerConfig {

    @Value("${springdoc.version}")
    private String appVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(apiComponent())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Beye app REST APIs")
                .description("List of APIs for Beye app.")
                .version(appVersion)
                .contact(new Contact()
                        .email("info@tunbor.com")
                        .name("Olakunle Awotunbo")
                        .url("www.tunbor.com"))
                .termsOfService("")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://tunbor.com"));
    }

    public Components apiComponent() {
        return new Components()
                .addSecuritySchemes("bearer-key", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT"));
    }
}
