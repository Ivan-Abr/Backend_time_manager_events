package com.example.application.config

import com.example.application.filter.HeadersLoggingFilter
import com.example.application.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyAppConfig {

    @Autowired
    private lateinit var userRepo: UserRepo
    @Autowired
    private lateinit var envConfig: EnvConfig

    @Bean
    fun registrationBean(): FilterRegistrationBean<HeadersLoggingFilter>? {
        val registrationBean: FilterRegistrationBean<HeadersLoggingFilter> =
                FilterRegistrationBean<HeadersLoggingFilter>()
        registrationBean.setFilter(
                HeadersLoggingFilter(userRepo,envConfig)
        )
        registrationBean.order = -1
        registrationBean.setName("fooBar")
        registrationBean.setUrlPatterns(listOf("/timemanager/*"))
        return registrationBean
    }


}