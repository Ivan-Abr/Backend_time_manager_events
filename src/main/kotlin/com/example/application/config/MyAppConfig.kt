package com.example.application.config

import com.example.application.filter.HeadersLoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyAppConfig {

    @Bean
    fun registrationBean(): FilterRegistrationBean<HeadersLoggingFilter>? {
        val registrationBean: FilterRegistrationBean<HeadersLoggingFilter> =
            FilterRegistrationBean<HeadersLoggingFilter>()
        registrationBean.setFilter(HeadersLoggingFilter())
        registrationBean.setOrder(-1)
        registrationBean.setName("fooBar")
        registrationBean.setUrlPatterns(listOf("/test/*", "/timemanager/*",""))
        return registrationBean
    }


}