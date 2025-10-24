package com.example.embeddings.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Long id;

    @NotBlank(message = "Название викторины обязательно")
    private String title;

    private String description;

    @NotNull(message = "Список вопросов не может быть пустым")
    private List<QuestionDto> questions;
}
