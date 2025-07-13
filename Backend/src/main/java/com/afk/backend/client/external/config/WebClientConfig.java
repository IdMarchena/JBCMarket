package com.afk.backend.client.external.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("googleMapsWebClient")
    public WebClient googleMapsWebClient(
            @Value("${external.apis.google-maps.base-url}") String baseUrl,
            @Value("${external.apis.google-maps.api-key}") String apiKey) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean("ubicacionWebClient")
    public WebClient ubicacionWebClient(
            @Value("${external.apis.google-maps.base-url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

}