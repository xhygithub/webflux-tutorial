package com.example.webfluxtutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
@SpringCloudApplication
@EnableWebFlux
@EnableReactiveFeignClients
public class WebfluxTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxTutorialApplication.class, args);
    }

}
