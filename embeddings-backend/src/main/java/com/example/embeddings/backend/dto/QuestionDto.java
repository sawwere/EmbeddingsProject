package com.example.embeddings.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;

    @NotBlank(message = "Текст вопроса обязателен")
    private String questionText;

    @NotBlank(message = "Правильный ответ обязателен")
    private String correctAnswer;
}
