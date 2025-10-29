package com.example.embeddings.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "embedding")
public class Embedding {
    @Id
    private Long questionId;

    private String model;

    @Column(name = "embedding_vector", columnDefinition = "vector")
    private float[] answerEmbedding;
}
