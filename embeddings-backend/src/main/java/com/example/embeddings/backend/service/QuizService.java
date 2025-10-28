package com.example.embeddings.backend.service;

import com.example.embeddings.backend.dto.QuizDto;
import com.example.embeddings.backend.entity.Question;
import com.example.embeddings.backend.entity.Quiz;
import com.example.embeddings.backend.repository.QuizRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public Quiz create(QuizDto dto) {
        return quizRepository.save(
                Quiz.builder()
                        .title(dto.getTitle())
                        .questions(
                                dto.getQuestions().stream().map(
                                        q -> Question.builder()
                                                .correctAnswer(q.getCorrectAnswer())
                                                .text(q.getQuestionText())
                                                .build()
                                ).toList()
                        )
                        .build()
        );
    }

    public Quiz findByIdOrThrow(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Нет викторины с таким айди"));
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }
}
