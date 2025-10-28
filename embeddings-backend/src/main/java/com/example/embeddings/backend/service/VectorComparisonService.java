package com.example.embeddings.backend.service;


import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class VectorComparisonService {

    public double calculateSimilarity(float[] vector1, float[] vector2, String method) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Векторы должны иметь одинаковую размерность");
        }

        return switch (method.toLowerCase()) {
            case "euclidean" -> euclideanSimilarity(vector1, vector2);
            case "manhattan" -> manhattanSimilarity(vector1, vector2);
            case "dot" -> dotProductSimilarity(vector1, vector2);
            default -> cosineSimilarity(vector1, vector2);
        };
    }

    private double cosineSimilarity(float[] vector1, float[] vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += Math.pow(vector1[i], 2);
            norm2 += Math.pow(vector2[i], 2);
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private double euclideanSimilarity(float[] vector1, float[] vector2) {
        double sum = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            sum += Math.pow(vector1[i] - vector2[i], 2);
        }
        double distance = Math.sqrt(sum);

        // Нормализуем расстояние в схожесть (0-1)
        return 1.0 / (1.0 + distance);
    }

    private double manhattanSimilarity(float[] vector1, float[] vector2) {
        double distance = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            distance += Math.abs(vector1[i] - vector2[i]);
        }

        // Нормализуем расстояние в схожесть (0-1)
        return 1.0 / (1.0 + distance);
    }

    private double dotProductSimilarity(float[] vector1, float[] vector2) {
        double dotProduct = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }

        // Нормализуем в диапазон 0-1 (это упрощенный подход)
        return (dotProduct + 1) / 2;
    }


    public Map<String, Double> compareWithAllMethods(float[] vector1, float[] vector2) {
        Map<String, Double> results = new HashMap<>();

        String[] methods = {"cosine", "euclidean", "manhattan", "dot"};
        for (String method : methods) {
            double similarity = calculateSimilarity(vector1, vector2, method);
            results.put(method, similarity);
        }
        return results;
    }
}
