package com.example.embeddings.backend.controller;

import com.example.embeddings.backend.dto.QuizDto;
import com.example.embeddings.backend.entity.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) {
        return ResponseEntity.ok(new Quiz());
    }
}
