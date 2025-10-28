package com.example.embeddings.backend.adapter;

import com.example.embeddings.backend.config.OllamaConfigProperties;
import com.example.embeddings.backend.dto.OllamaEmbeddingRequestDto;
import com.example.embeddings.backend.dto.OllamaEmbeddingResponseDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class OllamaAdapter {
    private String ollamaUrl;

    private RestClient restClient;

    public OllamaAdapter(RestClient resClient, OllamaConfigProperties configuration) {
        this.ollamaUrl = configuration.getUrl();
        this.restClient = RestClient.builder()
                .baseUrl(ollamaUrl)
                .build();
    }


    public float[] getEmbedding(String text, String model) {
        OllamaEmbeddingRequestDto request = new OllamaEmbeddingRequestDto(model, text);

        OllamaEmbeddingResponseDto response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OllamaEmbeddingResponseDto.class);

        return Objects.requireNonNull(response).getEmbedding();
    }
}
