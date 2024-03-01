package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizResponseRepository quizResponseRepository;

    public void processQuizSubmissions(QuizResponsesDTO quizResponsesDTO, Long userId) {
        for (QuizResponsesDTO.QuizResponseDTO responseDTO : quizResponsesDTO.getResponses()) {
            Optional<Quiz> quizOptional = quizRepository.findById(responseDTO.getQuizId());
            if (quizOptional.isPresent()) {
                Quiz quiz = quizOptional.get();
                boolean isCorrect = quiz.getCorrectAnswer().equals(responseDTO.getUserAnswer());
                int score = isCorrect ? quiz.getScore() : 0;

                QuizResponse quizResponse = new QuizResponse();
                quizResponse.setUserId(userId);
                quizResponse.setQuiz(quiz);
                quizResponse.setUserAnswer(responseDTO.getUserAnswer());
                quizResponse.setIsCorrect(isCorrect);
                quizResponse.setScore(score);

                quizResponseRepository.save(quizResponse); // 응답 객체 저장
            }
        }
        // 추가적인 결과 처리 로직 (예: 총점 계산 등)
    }
}
