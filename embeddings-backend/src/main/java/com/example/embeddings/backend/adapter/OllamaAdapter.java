package com.example.embeddings.backend.adapter;

import com.example.embeddings.backend.config.OllamaConfigProperties;
import com.example.embeddings.backend.dto.OllamaEmbeddingRequestDto;
import com.example.embeddings.backend.dto.OllamaEmbeddingResponseDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class OllamaAdapter {

    private final RestClient restClient;
    private final String model;

    public OllamaAdapter(
            @Value("${ollama.url:http://localhost:11434}") String baseUrl,
            @Value("${ollama.embedModel:nomic-embed-text}") String model
    ) {
        this.model = model;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        log.info("OllamaAdapter инициализирован. URL={}, model={}", baseUrl, model);
    }

    @Data
    public static class EmbeddingRequest {
        private String model;
        private String prompt;

        public EmbeddingRequest(String model, String prompt) {
            this.model = model;
            this.prompt = prompt;
        }
    }

    @Data
    public static class EmbeddingResponse {
        private List<Float> embedding;
    }

    public float[] getEmbedding(String text, String overrideModel) {
        String usedModel = (overrideModel != null) ? overrideModel : this.model;

        log.info("Запрос embedding у Ollama. Модель = {}", usedModel);

        EmbeddingRequest request = new EmbeddingRequest(usedModel, text);

        EmbeddingResponse response = restClient.post()
                .uri("/api/embeddings")
                .body(request)
                .retrieve()
                .body(EmbeddingResponse.class);

        if (response == null || response.getEmbedding() == null) {
            throw new RuntimeException("Ollama вернул пустой embedding");
        }

        List<Float> list = response.getEmbedding();
        float[] result = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }


    public String getDefaultModel() {
        return this.model;
    }
}
