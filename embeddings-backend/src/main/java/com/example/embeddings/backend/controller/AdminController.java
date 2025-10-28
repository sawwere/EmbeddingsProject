package com.example.embeddings.backend.controller;

import com.example.embeddings.backend.dto.QuizDto;
import com.example.embeddings.backend.entity.Quiz;
import com.example.embeddings.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final QuizService quizService;

    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) {
        return ResponseEntity.ok(quizService.create(quizDto));
    }
}
