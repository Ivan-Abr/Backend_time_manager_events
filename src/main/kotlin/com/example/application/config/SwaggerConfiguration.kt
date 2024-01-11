//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import springfox.documentation.builders.RequestHandlerSelectors
//import springfox.documentation.service.ApiInfo
//import springfox.documentation.service.Contact
//import springfox.documentation.spi.DocumentationType
//import springfox.documentation.spring.web.plugins.Docket
//import springfox.documentation.swagger2.annotations.EnableSwagger2
//
///**
// * Swagger configuration file
// */
//@Configuration // Load this configuration in SpringBoot configuration
//@EnableSwagger2 // Open Swagger2
//class SwaggerConfig {
//
//    // Configure the bean instance of the docket of Swagger
//    @Bean
//    fun docket(): Docket {
//        return Docket(DocumentationType.SWAGGER_2)
//                // Whether to turn on swagger
//                .enable(true)
//                .apiInfo(apiInfo())
//                .select()
//                // RequestHandlerSelectors configuration to scan the interface to scan the interface
//                // Basepackage specifies the package to be scanned
//                .apis(RequestHandlerSelectors.basePackage("Package names"))
//                .build()
//    }
//
//    // Configure Swagger Information Apiinfo
//    fun apiInfo(): ApiInfo {
//
//        //author information
//        val contact = Contact("Author's name", "Blog URL", "Mail")
//
//        return ApiInfo(
//                "title",
//                "describe",
//                "v1.0",
//                "Service Terms URL",
//                contact,
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                ArrayList()
//        )
//    }
//
//}