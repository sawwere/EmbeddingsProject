package com.example.embeddings.backend.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OllamaEmbeddingRequestDto {
    private String model;
    private String prompt;
}
