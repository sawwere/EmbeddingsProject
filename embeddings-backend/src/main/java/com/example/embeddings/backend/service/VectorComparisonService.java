package com.example.embeddings.backend.service;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
@Service
public class VectorComparisonService {

    public Map<String, Double> compareWithAllMethods(float[] v1, float[] v2) {
        Map<String, Double> results = new LinkedHashMap<>();
        results.put("cosine", cosineSimilarity(v1, v2));
        results.put("dot", dotProductSimilarity(v1, v2));
        results.put("euclidean", euclideanSimilarity(v1, v2));
        results.put("manhattan", manhattanSimilarity(v1, v2));
        return results;
    }

    /** Косинусное сходство (0..1 после нормализации) */
    private double cosineSimilarity(float[] v1, float[] v2) {
        double dot = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dot += v1[i] * v2[i];
            norm1 += v1[i] * v1[i];
            norm2 += v2[i] * v2[i];
        }
        if (norm1 == 0 || norm2 == 0) return 0.0;
        double rawCos = dot / (Math.sqrt(norm1) * Math.sqrt(norm2)); // [-1..1]
        return (rawCos + 1.0) / 2.0; // 0..1
    }

    /** Евклидово сходство (инвертируем расстояние → схожесть 0..1) */
    private double euclideanSimilarity(float[] v1, float[] v2) {
        double sumSq = 0.0;
        for (int i = 0; i < v1.length; i++) {
            double diff = v1[i] - v2[i];
            sumSq += diff * diff;
        }
        double dist = Math.sqrt(sumSq);
        double normDist = dist / v1.length; // нормализуем по размерности
        return 1.0 / (1.0 + normDist);
    }

    /** Манхэттенское сходство (инвертируем расстояние → схожесть 0..1) */
    private double manhattanSimilarity(float[] v1, float[] v2) {
        double sum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.abs(v1[i] - v2[i]);
        }
        double normDist = sum / v1.length;
        return 1.0 / (1.0 + normDist);
    }

    /** Скалярное сходство — без нормализации по длине (0..1 после сигмоиды) */
    private double dotProductSimilarity(float[] v1, float[] v2) {
        double dot = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dot += v1[i] * v2[i];
        }

        return 1.0 / (1.0 + Math.exp(-dot));
    }
}
