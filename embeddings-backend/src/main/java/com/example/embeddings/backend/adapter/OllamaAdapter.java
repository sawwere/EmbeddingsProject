package com.example.embeddings.backend.adapter;

import com.example.embeddings.backend.config.OllamaConfiguration;
import com.example.embeddings.backend.dto.OllamaEmbeddingRequestDto;
import com.example.embeddings.backend.dto.OllamaEmbeddingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class OllamaAdapter {
    private final String ollamaUrl;

    private final RestClient restClient;

    public OllamaAdapter(OllamaConfiguration configuration) {
        this.ollamaUrl = configuration.getUrl();
        this.restClient = RestClient.builder()
                .baseUrl(ollamaUrl)
                .build();
    }


    public List<Float> getEmbedding(String text, String model) {
        OllamaEmbeddingRequestDto request = new OllamaEmbeddingRequestDto(model, text);

        OllamaEmbeddingResponseDto response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OllamaEmbeddingResponseDto.class);

        return Objects.requireNonNull(response).getEmbedding();
    }
}
