package com.example.embeddings.backend.repository;

import com.example.embeddings.backend.entity.Embedding;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO embedding(question_id, embedding_vector, model)
        VALUES (:questionId, CAST(:vector AS vector), :model)
        ON CONFLICT (question_id) DO UPDATE
        SET embedding_vector = EXCLUDED.embedding_vector,
            model = EXCLUDED.model
        """, nativeQuery = true)
    void upsert(@Param("questionId") Long questionId,
                @Param("model") String model,
                @Param("vector") String vector);
}