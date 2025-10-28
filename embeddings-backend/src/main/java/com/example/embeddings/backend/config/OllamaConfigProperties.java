package com.example.embeddings.backend.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "ollama")
public class OllamaConfigProperties {
    private String url;
    private String model;
}
