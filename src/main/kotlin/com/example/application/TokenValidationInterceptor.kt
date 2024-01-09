package com.example.application

import com.example.application.exceptions.TokenValidationException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor

class TokenValidationInterceptor: HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader("Authorization")
        if (!isValidToken(token)){
            println("Debil")
            //throw TokenValidationException("Token Validation Exception", HttpStatus.EXPECTATION_FAILED )
            throw IllegalStateException("OAUOAUFJOFAUOIEJAOIFUIAUFJAOIPUFAF")
        }
        return true
    }

    private fun isValidToken(token: String?): Boolean{
        return false
    }


}