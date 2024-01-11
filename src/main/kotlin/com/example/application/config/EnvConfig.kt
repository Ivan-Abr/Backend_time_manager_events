package com.example.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EnvConfig {
    @Value("\${SERVER_BACKEND_AUTH_API}")
    lateinit var SERVER_BACKEND_AUTH_API: String
}

