package com.example.webfluxtutorial.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class HeaderInterceptorConfiguration {
    @Bean
    HeaderInterceptor HeaderInterceptorConfiguration() {
      return new HeaderInterceptor();
    }
}
