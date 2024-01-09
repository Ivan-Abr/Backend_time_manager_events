package com.example.application

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class TokenValidationAspect {

    fun tokenCheck(token: String):Boolean{
        if (token == "Debil") return true
        return false
    }
    @Before("execution(* com.example.application.controller.UserController.*(..)) &&" +
            "args(token, ..)")
    fun validateToken(token: String){
        if (!tokenCheck(token)){
            println("Debil")
            throw IllegalStateException("Invalid token")
        }
        else println("Dolboeb")
    }


}