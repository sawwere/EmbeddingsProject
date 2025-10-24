package com.example.embeddings.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDto {
    @NotNull(message = "ID вопроса обязателен")
    private Long questionId;

    @NotBlank(message = "Ответ пользователя не может быть пустым")
    private String userAnswer;
}
