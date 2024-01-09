package com.example.application.config

import com.example.application.entity.Tag
import com.example.application.repository.TagRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TagConfig {
    @Bean
    fun commandLineRunnerTag(@Autowired tagRepo: TagRepo): CommandLineRunner{
        return CommandLineRunner {
            var tag1 = Tag(1L,"testTag","testTagDesc","#FFFF",null)
            tagRepo.saveAll(listOf(tag1))
        }
    }
}