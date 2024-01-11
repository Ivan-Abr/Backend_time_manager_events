package com.example.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class TimemanagerApplication

fun main(args: Array<String>) {
	runApplication<TimemanagerApplication>(*args)
}
