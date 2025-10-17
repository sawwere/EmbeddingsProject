package com.example.embeddings.backend.repository;

import com.example.embeddings.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}