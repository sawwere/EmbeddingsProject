package com.example.embeddings.backend.service;

import com.example.embeddings.backend.dto.QuizDto;
import com.example.embeddings.backend.entity.Question;
import com.example.embeddings.backend.entity.Quiz;
import com.example.embeddings.backend.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public Quiz create(QuizDto dto) {
        Quiz quiz = Quiz.builder()
                .title(dto.getTitle())
                .questions(new ArrayList<>())
                .build();

        dto.getQuestions().forEach(q -> {
            Question question = Question.builder()
                    .text(q.getQuestionText())
                    .correctAnswer(q.getCorrectAnswer())
                    .quiz(quiz)
                    .build();
            quiz.getQuestions().add(question);
        });

        return quizRepository.save(quiz);
    }

    public Quiz update(Long id, QuizDto dto) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Викторина не найдена: " + id));

        quiz.setTitle(dto.getTitle());

        quiz.getQuestions().clear();

        dto.getQuestions().forEach(q -> {
            Question question = Question.builder()
                    .text(q.getQuestionText())
                    .correctAnswer(q.getCorrectAnswer())
                    .quiz(quiz)
                    .build();
            quiz.getQuestions().add(question);
        });

        return quizRepository.save(quiz);
    }

    public Quiz findByIdOrThrow(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Нет викторины с таким айди"));
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
