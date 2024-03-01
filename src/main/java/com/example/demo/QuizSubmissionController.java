package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
public class QuizSubmissionController {

    @Autowired
    private QuizService quizService; // 서비스 레이어를 주입받습니다.

    @PostMapping("/submit-responses")
    public ResponseEntity<?> submitQuizResponses(@RequestBody QuizResponsesDTO quizResponsesDTO,
                                                 @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getClaimAsString("user_id")); // JWT에서 사용자 ID 추출
        quizService.processQuizSubmissions(quizResponsesDTO, userId); // 서비스 레이어에 처리 위임
        return ResponseEntity.ok().build(); // 처리 결과 반환
    }
}


