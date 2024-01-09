package com.example.application.exceptions

class TokenValidationException(errorMessage: String, err: Throwable): Exception(errorMessage)