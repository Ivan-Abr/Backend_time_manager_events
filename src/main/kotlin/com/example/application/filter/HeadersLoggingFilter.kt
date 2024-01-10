package com.example.application.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException



//@Component
@Slf4j
class HeadersLoggingFilter: OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        Collections.list(request.headerNames)
//            .forEach { header ->
//                println("Header: $header=${request.getHeader(header)}")
//            }
//        filterChain.doFilter(request, response)
        var header = ""
        header += request.getHeader("Authorization")
        println(header)
        var response2 = (sendGetRequestWithRestTemplate(header))
        if (response2 is HttpClientErrorException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }
        else {
            filterChain.doFilter(request,response)
        }


    }



//    fun sendGetRequest() {
//        val restTemplate = RestTemplate()
//        val response = restTemplate.getForObject("http://localhost:8081/dm/v1/answer/all/org/1", String::class.java)
//        println("Response: $response")
//    }


    fun sendGetRequestWithRestTemplate(token: String): Any {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.set("Authorization", "$token")

        val entity = HttpEntity<String>(headers)

        try {
            val response = restTemplate.exchange(
                "http://85.175.194.251:50443/api/auth-service/auth/internal-auth",
                HttpMethod.POST,
                entity,
                String::class.java
            )
            return response
        } catch (e: HttpClientErrorException) {
            return e
        }
    }





}