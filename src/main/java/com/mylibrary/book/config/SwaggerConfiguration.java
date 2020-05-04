package com.mylibrary.book.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class allowing to define Swagger API for the project
 *
 * @author Bharadwaj Adepu
 */
@SuppressWarnings({"unused"})
@Configuration
public class SwaggerConfiguration {

    private static final String DEFAULT_REST_PACKAGE = "com.mylibrary.book.rest";

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(this.applicationName)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(DEFAULT_REST_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My Library API")
                .description("SpringBoot Microservice PoC REST API")
                .contact(new Contact("Bharadwaj Adepu", "www.infosys.com", "bharadwaj.adepu@infosys.com"))
                .version("1.0") 
                .build();
    }
}
