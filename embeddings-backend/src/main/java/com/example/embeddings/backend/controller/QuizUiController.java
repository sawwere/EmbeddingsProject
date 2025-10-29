package com.example.embeddings.backend.controller;

import com.example.embeddings.backend.dto.UserAnswerDto;
import com.example.embeddings.backend.entity.Quiz;
import com.example.embeddings.backend.service.AnswerValidationService;
import com.example.embeddings.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz-ui")
@RequiredArgsConstructor
public class QuizUiController {
    private final QuizService quizService;
    private final AnswerValidationService validationService;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("quizzes", quizService.findAll());
        return "quiz-list";
    }

    @GetMapping("/{quizId}")
    public String showQuiz(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.findByIdOrThrow(quizId);
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    @PostMapping("/{questionId}/submit")
    public String submitAnswer(@PathVariable Long questionId,
                               @RequestParam("answer") String answer,
                               Model model) {
        var result = validationService.validateAnswer(questionId,
                new UserAnswerDto(questionId, answer));
        model.addAttribute("result", result);
        return "result";
    }
}

