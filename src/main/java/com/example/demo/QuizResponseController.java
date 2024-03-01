package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QuizResponseController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizResponseRepository quizResponseRepository;

    @PostMapping("/quizzes/{quizId}/response")
    public ResponseEntity<QuizResponse> submitQuizResponse(@PathVariable Long quizId,
                                                           @RequestBody QuizResponse quizResponseInput,
                                                           @AuthenticationPrincipal Jwt jwt) {
        // JWT 토큰에서 사용자 ID 추출 (예제에서는 userId가 String 타입으로 가정)
    	Long userId = Long.parseLong(jwt.getClaimAsString("user_id"));

        // 퀴즈 정보 조회
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (!quizOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Quiz quiz = quizOptional.get();

        // 정답 여부 판단
        boolean isCorrect = quiz.getCorrectAnswer().equals(quizResponseInput.getUserAnswer());

        // 획득한 점수 계산 (정답인 경우에만 점수 부여)
        int score = isCorrect ? quiz.getScore() : 0;

        // 퀴즈 응답 객체 생성 및 저장
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setUserId(userId); // userId 설정
        quizResponse.setQuiz(quiz); // quizId 설정
        quizResponse.setUserAnswer(quizResponseInput.getUserAnswer()); // 사용자 응답 설정
        quizResponse.setIsCorrect(isCorrect); // 정답 여부 설정
        quizResponse.setScore(score); // 점수 설정

        QuizResponse savedResponse = quizResponseRepository.save(quizResponse);

        // 결과 반환
        return ResponseEntity.ok(savedResponse);
    }
    @GetMapping("/users/{userId}/quiz-details-and-score")
    public ResponseEntity<Map<String, Object>> getRecentQuizDetailsAndScore(@PathVariable Long userId, @RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "id"));
        Page<QuizResponse> pageResponses = quizResponseRepository.findByUserIdOrderByIdDesc(userId, pageable);
        List<QuizResponse> responses = new ArrayList<>(pageResponses.getContent()); // 변경 불가능한 리스트를 새로운 리스트로 복사
        
        // 응답 리스트를 뒤집어서 처음 푼 문제가 위에 오도록 함
        Collections.reverse(responses);

        List<Map<String, Object>> detailsList = new ArrayList<>();
        int totalScore = 0;
        for (QuizResponse response : responses) {
            Map<String, Object> details = new HashMap<>();
            details.put("question", response.getQuiz().getQuestionText());
            details.put("options", response.getQuiz().getOptions());
            details.put("correctAnswer", response.getQuiz().getCorrectAnswer());
            details.put("userAnswer", response.getUserAnswer());
            details.put("isCorrect", response.getIsCorrect());
            details.put("explanation", response.getQuiz().getExplanation());
            details.put("score", response.getQuiz().getScore());
            detailsList.add(details);

            totalScore += response.getScore();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("quizDetails", detailsList);
        result.put("totalScore", totalScore);

        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/quiz-responses/{id}")
    public ResponseEntity<?> deleteQuizResponse(@PathVariable Long id) {
        return quizResponseRepository.findById(id)
                .map(quizResponse -> {
                    quizResponseRepository.delete(quizResponse);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/quiz-responses/all")
    public ResponseEntity<?> deleteAllQuizResponses() {
        quizResponseRepository.deleteAll();
        return ResponseEntity.ok().build(); // 모든 데이터 삭제 후, 200 OK 응답 반환
    }
}
