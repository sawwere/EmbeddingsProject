package com.example.embeddings.backend.service;

import com.example.embeddings.backend.adapter.OllamaAdapter;
import com.example.embeddings.backend.config.OllamaConfigProperties;
import com.example.embeddings.backend.dto.AnswerResultDto;
import com.example.embeddings.backend.dto.UserAnswerDto;
import com.example.embeddings.backend.entity.Question;
import com.example.embeddings.backend.repository.EmbeddingRepository;
import com.example.embeddings.backend.repository.QuestionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerValidationService {

    private final QuestionRepository questionRepository;
    private final EmbeddingRepository embeddingRepository;

    @Autowired
    private final OllamaAdapter ollamaAdapter;
    private final OllamaConfigProperties ollamaConfiguration;

    @Autowired
    private VectorComparisonService vectorComparisonService;

    @Value("${quiz.similarity.threshold:0.8}")
    private double defaultSimilarityThreshold;

    /**
     * Проверяет ответ пользователя на вопрос викторины
     */
    public AnswerResultDto validateAnswer(Long questionId, UserAnswerDto userAnswer) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isEmpty()) {
            throw new RuntimeException("Вопрос не найден с ID: " + questionId);
        }

        Question question = questionOpt.get();
        String correctAnswer = question.getCorrectAnswer();

        // Получаем или вычисляем эмбеддинг правильного ответа
        float[] correctEmbedding = getOrComputeEmbedding(correctAnswer, ollamaConfiguration.getModel(), question);
        // Вычисляем эмбеддинг ответа пользователя
        float[] userEmbedding = ollamaAdapter.getEmbedding(userAnswer.getUserAnswer(), ollamaConfiguration.getModel());
        // Сравниваем эмбеддинги
        var similarity = vectorComparisonService.compareWithAllMethods(correctEmbedding, userEmbedding);


        var distanceResult = similarity.keySet().stream().map(distMethod -> new AnswerResultDto.DistanceResult(
                similarity.get(distMethod) >= defaultSimilarityThreshold,
                similarity.get(distMethod),
                distMethod
        )).toList();
        return AnswerResultDto.builder()
                .questionId(question.getId())
                .userAnswer(userAnswer.getUserAnswer())
                .correctAnswer(question.getCorrectAnswer())
                .distanceResult(distanceResult)
                .build();
    }

    /**
     * Получает существующий эмбеддинг или вычисляет новый
     */
    private float[] getOrComputeEmbedding(String text, String model, Question question) {
        // TODO
        // Если у вопроса уже есть эмбеддинг для этой модели, используем его
//        if (question.getEmbedding() != null &&
//                model.equals(question.getEmbeddingModel())) {
//            return question.getEmbedding();
//        }

        // Иначе вычисляем новый эмбеддинг и сохраняем в базу
        float[] embedding = ollamaAdapter.getEmbedding(text, model);

        // Обновляем вопрос с новым эмбеддингом
//        question.setEmbedding(embedding);
//        question.setEmbeddingModel(model);
//        embeddingRepository.upsert(question.getId(), );

        return embedding;
    }
}