package com.example.embeddings.backend.controller;

import com.example.embeddings.backend.dto.AnswerResultDto;
import com.example.embeddings.backend.dto.UserAnswerDto;
import com.example.embeddings.backend.entity.Quiz;
import com.example.embeddings.backend.service.AnswerValidationService;
import com.example.embeddings.backend.service.QuizService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;
    private final AnswerValidationService answerValidationService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(
                quizService.findAll()
        );
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable(name = "quizId") Long quizId) {
        return ResponseEntity.ok(quizService.findByIdOrThrow(quizId));
    }

    @PostMapping("/{questionId}/submit")
    public ResponseEntity<AnswerResultDto> submitAnswer(
            @PathVariable Long questionId,
            @RequestBody UserAnswerDto userAnswer) {
        return ResponseEntity.ok(answerValidationService.validateAnswer(questionId, userAnswer));
    }
}
