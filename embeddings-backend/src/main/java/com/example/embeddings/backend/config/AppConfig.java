package com.example.embeddings.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties(OllamaConfigProperties.class)
public class AppConfig {
    private OllamaConfigProperties ollama = new OllamaConfigProperties();

}
