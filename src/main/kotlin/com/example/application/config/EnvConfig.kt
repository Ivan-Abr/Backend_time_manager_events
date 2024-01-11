package com.example.application.config

class EnvConfig {
    val SERVER_BACKEND_AUTH_API = System.getenv("SERVER_BACKEND_AUTH_API") ?: "http://85.175.194.251:50443/api/auth-service"
}

