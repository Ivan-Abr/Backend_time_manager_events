package com.example.application.swagger

import io.swagger.v3.oas.models.security.SecurityScheme

private fun createAPIKeyScheme(): SecurityScheme? {
    return SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer")
}
class OpenApi {
}