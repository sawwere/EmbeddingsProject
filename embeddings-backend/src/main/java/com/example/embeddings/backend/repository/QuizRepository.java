package com.example.embeddings.backend.repository;

import com.example.embeddings.backend.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}