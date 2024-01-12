package com.example.application.filter

import USER_REQUEST_KEY
import com.example.application.config.EnvConfig
import com.example.application.entity.User
import com.example.application.repository.UserRepo
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*

@Slf4j
@Component
class HeadersLoggingFilter(
        private var userRepo: UserRepo,
        private var envConfig: EnvConfig
) : OncePerRequestFilter() {
    private var serverAuthApiAddress: String = envConfig.SERVER_BACKEND_AUTH_API

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain,
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.status = HttpServletResponse.SC_OK
        if (request.servletPath.contains("swagger") || request.servletPath.contains("api")) {
            filterChain.doFilter(request, response)
            return
        }
        var token = ""
        token += request.getHeader("Authorization")
        val response2 = (sendPostInternalAuthRequestWithRestTemplate(token))
        if (response2 is HttpClientErrorException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }
        val responseGetUser = sendGetUserRequestWithRestTemplate(token)
        if (responseGetUser is HttpClientErrorException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }
        if (responseGetUser is ResponseEntity<*>) {
            val tmp: String = responseGetUser.body.toString()
            try {
                if (!userRepo.existsById(UUID.fromString(JSONObject(tmp).get("id") as String))) {
                    println("govnon")
                    throw IllegalStateException("User with this id does not exists")
                }
            } catch (error: Throwable) {
                userRepo.save(User(
                        userId = UUID.fromString(JSONObject(tmp).get("id") as String),
                        events = null
                ))
            }
            request.setAttribute(USER_REQUEST_KEY, UUID.fromString(JSONObject(tmp).get("id") as String))
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun sendGetUserRequestWithRestTemplate(token: String): Any {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val entity = HttpEntity<String>(headers)

        return try {
            val response = restTemplate.exchange(
                    "$serverAuthApiAddress/user/me",
                    HttpMethod.GET,
                    entity,
                    String::class.java
            )
            response
        } catch (e: HttpClientErrorException) {
            e
        }
    }

    private fun sendPostInternalAuthRequestWithRestTemplate(token: String): Any {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val entity = HttpEntity<String>(headers)

        return try {
            val response = restTemplate.exchange(
                    "$serverAuthApiAddress/auth/internal-auth",
                    HttpMethod.POST,
                    entity,
                    String::class.java
            )
            response
        } catch (e: HttpClientErrorException) {
            e
        }
    }

}