package com.example.application.config

import com.example.application.entity.Notification
import com.example.application.repository.NotificationRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class NotificationConfig {
    @Bean
    fun commandLineRunnerNote(@Autowired notificationRepo: NotificationRepo): CommandLineRunner{
        return CommandLineRunner {
            var note1 = Notification(1L, LocalDate.now(), null)
        }
    }
}