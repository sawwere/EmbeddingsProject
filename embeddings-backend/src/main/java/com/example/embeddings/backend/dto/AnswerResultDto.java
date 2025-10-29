package com.example.embeddings.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double similarity;
    private List<DistanceResult> distanceResult;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistanceResult {
        private boolean isCorrect;
        private double similarityScore;
        private String distanceMethod;
    }
}
