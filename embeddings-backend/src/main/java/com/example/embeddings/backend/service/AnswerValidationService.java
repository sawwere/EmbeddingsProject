package com.example.embeddings.backend.service;

import com.example.embeddings.backend.adapter.OllamaAdapter;
import com.example.embeddings.backend.config.OllamaConfigProperties;
import com.example.embeddings.backend.dto.AnswerResultDto;
import com.example.embeddings.backend.dto.UserAnswerDto;
import com.example.embeddings.backend.entity.Question;
import com.example.embeddings.backend.repository.EmbeddingRepository;
import com.example.embeddings.backend.repository.QuestionRepository;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerValidationService {

    private final OllamaAdapter ollamaAdapter;
    private final QuestionRepository questionRepository;
    private final EmbeddingRepository embeddingRepository;
    private final VectorComparisonService vectorService;

    @Value("${quiz.similarity.threshold:0.8}")
    private double threshold;

    public AnswerResultDto validateAnswer(Long questionId, UserAnswerDto userAnswer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Вопрос не найден с ID: " + questionId));

        String correct = preprocess(question.getCorrectAnswer());
        String userText = preprocess(userAnswer.getUserAnswer());

        float[] correctEmbedding = getOrComputeEmbedding(correct, question);
        float[] userEmbedding = ollamaAdapter.getEmbedding(userText, null);

        Map<String, Double> metrics = vectorService.compareWithAllMethods(correctEmbedding, userEmbedding);

        long passed = metrics.values().stream()
                .filter(v -> v >= threshold)
                .count();
        boolean isCorrect = passed >= 3;

        List<AnswerResultDto.DistanceResult> distanceResults = metrics.entrySet().stream()
                .map(e -> new AnswerResultDto.DistanceResult(
                        e.getValue() >= threshold,
                        e.getValue(),
                        e.getKey()))
                .toList();

        return AnswerResultDto.builder()
                .questionId(questionId)
                .userAnswer(userText)
                .correctAnswer(correct)
                .isCorrect(isCorrect)
                .similarity(metrics.get("cosine")) // просто для отображения
                .distanceResult(distanceResults)
                .build();
    }

    private String preprocess(String text) {
        if (text == null) return "";
        return text.toLowerCase(Locale.ROOT)
                .replaceAll("[.,!?;:()\"'«»]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private float[] getOrComputeEmbedding(String text, Question question) {
        float[] embedding = ollamaAdapter.getEmbedding(text, null);
        String vectorLiteral = toPgVectorLiteral(embedding);
        embeddingRepository.upsert(question.getId(), "nomic-embed-text", vectorLiteral);
        return embedding;
    }

    private String toPgVectorLiteral(float[] v) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < v.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(v[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
