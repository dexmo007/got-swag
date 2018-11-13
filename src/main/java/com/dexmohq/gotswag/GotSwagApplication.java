package com.dexmohq.gotswag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
@EnableSwagger2
public class GotSwagApplication {

    @Value("classpath:pom.properties")
    private Resource pomProperties;

    @Bean
    public Docket docket() throws IOException {
        final Properties properties = new Properties();
        properties.load(pomProperties.getInputStream());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dexmohq"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo(
                        "webhooker", "Hooking the web", properties.getProperty("version"), null,
                        new Contact("Henrik Drefs", "https://github.com/dexmo007", null),
                        properties.getProperty("license.name"), properties.getProperty("license.url"), Collections.emptyList()
                ))
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                ;
    }

    public static void main(String[] args) {
        SpringApplication.run(GotSwagApplication.class, args);
    }
}
