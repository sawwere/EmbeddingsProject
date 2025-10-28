package com.example.embeddings.backend.repository;

import com.example.embeddings.backend.entity.Embedding;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface EmbeddingRepository extends Repository<Embedding, Long> {


    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO embedding(question_id, answer_embedding)
            VALUES (:qid, CAST(:vec AS vector))
            ON CONFLICT (question_id) DO UPDATE
            SET answer_embedding = EXCLUDED.answer_embedding
            """, nativeQuery = true)
    void upsert(@Param("qid") long questionId,
                @Param("vec") String vectorLiteral);



}
