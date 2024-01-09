package com.example.application.config

import com.example.application.entity.Event
import com.example.application.repository.EventRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventConfig {
    @Bean
    fun commandLineRunnerEvent(@Autowired eventRepo: EventRepo): CommandLineRunner{
        return CommandLineRunner {
            var event1 = Event(1L,"","", null,null, null)
            eventRepo.saveAll(listOf(event1))
        }
    }
}