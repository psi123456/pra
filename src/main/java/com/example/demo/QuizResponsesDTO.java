package com.example.demo;

import java.util.List;

public class QuizResponsesDTO {
    private List<QuizResponseDTO> responses;

    // 내부 클래스로 QuizResponseDTO 정의
    public static class QuizResponseDTO {
        private Long quizId;
        private String userAnswer;

        // Getters and Setters
        public Long getQuizId() {
            return quizId;
        }

        public void setQuizId(Long quizId) {
            this.quizId = quizId;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }
    }

    // Getters and Setters
    public List<QuizResponseDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<QuizResponseDTO> responses) {
        this.responses = responses;
    }
}
