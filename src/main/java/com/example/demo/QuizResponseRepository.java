package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {
    // 특정 사용자가 제출한 모든 퀴즈 응답 찾기
    List<QuizResponse> findByUserId(Long userId);

    // 특정 퀴즈에 대한 모든 응답 찾기
    List<QuizResponse> findByQuizId(Long quizId);
    
    // 특정 사용자의 최근 N개 응답 조회
    List<QuizResponse> findByQuiz(Quiz quiz, Pageable pageable);
    
    Page<QuizResponse> findByUserId(Long userId, Pageable pageable);
    
    Page<QuizResponse> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
