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
    private List<DistanceResult> distanceResult;

    public static class DistanceResult {
        public boolean isCorrect;
        public double similarityScore;
        public String distanceMethod;

        public DistanceResult(boolean isCorrect, double similarityScore, String distanceMethod) {
            this.isCorrect = isCorrect;
            this.similarityScore = similarityScore;
            this.distanceMethod = distanceMethod;
        }
    }
}
