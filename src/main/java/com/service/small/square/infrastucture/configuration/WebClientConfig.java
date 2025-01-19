package com.service.small.square.infrastucture.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/owner").build();
    }

    @Bean
    WebClient employeRestaurantWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/employeerestaurant").build();
    }

    @Bean
    WebClient userWebClient(){
        return WebClient.builder().baseUrl("http://localhost:8081/owner/users").build();

    }

    @Bean
    WebClient orderWebClient(){
        return WebClient.builder().baseUrl("http://localhost:8088/api/orders").build();
    }
}