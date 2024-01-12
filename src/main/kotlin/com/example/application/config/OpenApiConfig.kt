package com.example.application.config

import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    info = Info(
        contact = Contact(
            name = "Vanchez",
            email = "ivabramov@gamil.com",
            url = "https://youtu.be/kX-wJ-DfNxA?si=vJ3kiIDokL0roOav",

        ),
        description = "OpenApi documentation for application",
        title = "OpenApi Spec",
        version = "1.0",
        license = License(
            name = "Licence name",
            url = "https://license.com"
        ),

        //termsOfService = "Terms of service" ,




    ),
    servers = [
        Server(
            description = "Local ENV",
            url = "http://localhost:8083"
        )
    ]
)
@SecurityScheme(
    name = "bearerAuth",
    description = "Enter JWT token here",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
class OpenApiConfig {
}