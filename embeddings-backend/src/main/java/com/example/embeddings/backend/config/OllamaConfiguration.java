package com.example.embeddings.backend.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "ollama")
@Configuration
@Getter
@Setter
public class OllamaConfiguration {
    private final String url;
    private final String model;
}
