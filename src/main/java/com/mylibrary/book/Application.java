package com.mylibrary.book;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main starting point of the application.
 * SpringBoot and SpringCloud features are enabled through annotations on {@link Application} class.
 * TODO: replace application description here
 *
 * @author Bharadwaj Adepu
 * @see SpringBootApplication
 * @see EnableSwagger2
 * <p>
 * @since 1.0-SNAPSHOT
 */
@SpringBootApplication
@EnableSwagger2
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class Application {

    /**
     * Main starting point of the microservice.
     *
     * @param args an array of arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
