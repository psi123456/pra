package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // 문제 텍스트에 특정 문자열이 포함된 퀴즈 문제 찾기
    List<Quiz> findByQuestionTextContaining(String questionText);
}
