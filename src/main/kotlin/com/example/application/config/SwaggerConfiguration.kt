import com.google.common.collect.Lists
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.core.converters.models.Pageable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime


@Configuration
@EnableSwagger2
@Import(
    BeanValidatorPluginsConfiguration::class
)
class SwaggerConfiguration {
    private val log: Logger = LoggerFactory.getLogger(SwaggerConfiguration::class.java)
    @Bean
    fun swaggerSpringfoxDocket(): Docket {
        log.debug("Starting Swagger")
        val contact = Contact(
            "Matyas Albert-Nagy",
            "https://justrocket.de",
            "matyas@justrocket.de"
        )
        val vext: List<VendorExtension<*>> = ArrayList()
        val apiInfo = ApiInfo(
            "Backend API",
            "This is the best stuff since sliced bread - API",
            "6.6.6",
            "https://justrocket.de",
            contact,
            "MIT",
            "https://justrocket.de",
            vext
        )
        var docket = Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .pathMapping("/")
            .apiInfo(ApiInfo.DEFAULT)
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity::class.java)
            .ignoredParameterTypes(Pageable::class.java)
            .ignoredParameterTypes(Date::class.java)
            .directModelSubstitute(LocalDate::class.java, Date::class.java)
            .directModelSubstitute(ZonedDateTime::class.java, Date::class.java)
            .directModelSubstitute(LocalDateTime::class.java, Date::class.java)
            .securityContexts(Lists.newArrayList<SecurityContext>(securityContext()))
            .securitySchemes(Lists.newArrayList<ApiKey>(apiKey()))
            .useDefaultResponseMessages(false)
        docket = docket.select()
            .paths(regex(DEFAULT_INCLUDE_PATTERN))
            .build()
        return docket
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", AUTHORIZATION_HEADER, "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
            .build()
    }

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
            SecurityReference("JWT", authorizationScopes)
        )
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val DEFAULT_INCLUDE_PATTERN = "/timemanager/.*"
    }
}




