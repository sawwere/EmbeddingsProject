package com.example.embeddings.backend.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResultDto {
    private Long questionId;
    private String userAnswer;
    private String correctAnswer;
    private boolean isCorrect;
    private double similarityScore;
    private String distanceMethod;
}
