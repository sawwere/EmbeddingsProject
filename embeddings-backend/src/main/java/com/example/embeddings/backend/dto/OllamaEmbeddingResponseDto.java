package com.example.embeddings.backend.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OllamaEmbeddingResponseDto {
    private String model;
    private List<Float> embedding;
}
