package com.example.embeddings.backend.controller;

import com.example.embeddings.backend.dto.AnswerResultDto;
import com.example.embeddings.backend.dto.UserAnswerDto;
import com.example.embeddings.backend.entity.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(List.of(new Quiz()));
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable(name = "quizId") Long quizId) {
        return ResponseEntity.ok(new Quiz());
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<List<AnswerResultDto>> submitAnswer(
            @PathVariable Long quizId,
            @RequestBody UserAnswerDto userAnswer) {
        return ResponseEntity.ok(List.of(new AnswerResultDto()));
    }
}
