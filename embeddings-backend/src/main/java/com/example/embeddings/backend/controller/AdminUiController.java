package com.example.embeddings.backend.controller;

import com.example.embeddings.backend.dto.QuestionDto;
import com.example.embeddings.backend.dto.QuizDto;
import com.example.embeddings.backend.dto.UserAnswerDto;
import com.example.embeddings.backend.entity.Quiz;
import com.example.embeddings.backend.service.AnswerValidationService;
import com.example.embeddings.backend.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminUiController {

    private final QuizService quizService;
    private final AnswerValidationService answerValidationService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("quizzes", quizService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/quizzes/new")
    public String newQuizForm(Model model) {
        QuizDto dto = new QuizDto();
        dto.setQuestions(new ArrayList<>(List.of(
                QuestionDto.builder().questionText("").correctAnswer("").build()
        )));
        model.addAttribute("quizDto", dto);
        model.addAttribute("title", "Новая викторина");
        return "admin/quiz-form";
    }

    @PostMapping("/quizzes")
    public String createQuiz(@Valid @ModelAttribute("quizDto") QuizDto quizDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Новая викторина");
            return "admin/quiz-form";
        }
        quizService.create(quizDto);
        return "redirect:/admin";
    }

    @GetMapping("/quizzes/{id}/edit")
    public String editQuiz(@PathVariable Long id, Model model) {
        Quiz q = quizService.findByIdOrThrow(id);
        QuizDto dto = QuizDto.builder()
                .id(q.getId())
                .title(q.getTitle())
                .questions(q.getQuestions().stream()
                        .map(qq -> QuestionDto.builder()
                                .id(qq.getId())
                                .questionText(qq.getText())
                                .correctAnswer(qq.getCorrectAnswer())
                                .build())
                        .toList())
                .build();
        model.addAttribute("quizDto", dto);
        model.addAttribute("title", "Редактировать викторину");
        return "admin/quiz-form";
    }

    @PostMapping("/quizzes/{id}")
    public String updateQuiz(@PathVariable Long id,
                             @Valid @ModelAttribute("quizDto") QuizDto quizDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Редактировать викторину");
            return "admin/quiz-form";
        }

        quizService.update(id, quizDto);
        return "redirect:/admin";
    }


    @PostMapping("/quizzes/{id}/delete")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/quizzes/{id}")
    public String quizDetails(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findByIdOrThrow(id);
        model.addAttribute("quiz", quiz);
        return "admin/quiz-details";
    }

    @PostMapping("/quizzes/{id}/warmup")
    public String warmupEmbeddings(@PathVariable Long id) {
        Quiz quiz = quizService.findByIdOrThrow(id);
        quiz.getQuestions().forEach(q -> {
            UserAnswerDto dto = new UserAnswerDto(q.getId(), q.getCorrectAnswer());
            answerValidationService.validateAnswer(q.getId(), dto);
        });
        return "redirect:/admin/quizzes/" + id + "?warmed";
    }

}
