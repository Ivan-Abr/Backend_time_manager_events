package com.example.application.config

import com.example.application.entity.Event
import com.example.application.repository.EventRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class EventConfig {
    @Bean
    fun commandLineRunnerEvent(@Autowired eventRepo: EventRepo): CommandLineRunner{
        return CommandLineRunner {
            var event1 = Event(1L,"","", LocalDate.now(), null,null)
            eventRepo.saveAll(listOf(event1))
        }
    }
}