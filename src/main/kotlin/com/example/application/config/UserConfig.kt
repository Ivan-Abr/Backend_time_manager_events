package com.example.application.config

import com.example.application.entity.User
import com.example.application.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfig {
    @Bean
    fun commandLineRunnerUser(@Autowired userRepo: UserRepo): CommandLineRunner{
        return CommandLineRunner {
            val user1 = User(1L, "12345", "test", null)
            userRepo.saveAll(listOf(user1))
        }
    }
}