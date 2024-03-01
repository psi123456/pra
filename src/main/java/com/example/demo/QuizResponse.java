package com.example.demo;

import jakarta.persistence.*;


@Entity
@Table(name = "quiz_response")
public class QuizResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_response_id_seq")
    @SequenceGenerator(name = "quiz_response_id_seq", sequenceName = "quiz_response_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private Long userId; // JWT에서 추출된 사용자 ID

    @ManyToOne(fetch = FetchType.LAZY) // Quiz 엔티티와의 ManyToOne 관계 설정
    @JoinColumn(name = "quiz_id") // 외래 키 컬럼을 지정
    private Quiz quiz; // Quiz 엔티티 참조

    @Column(name = "user_answer")
    private String userAnswer; // 사용자가 선택한 답변

    @Column(name = "is_correct")
    private Boolean isCorrect; // 답변의 정답 여부

    @Column(name = "score")
    private Integer score; // 이 문제에 대한 점수

    public QuizResponse() {
    }

    public QuizResponse(Long userId, Quiz quiz, String userAnswer, Boolean isCorrect, Integer score) {
        this.userId = userId;
        this.quiz = quiz; // Quiz 엔티티 참조 설정
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
        this.score = score;
    }

    // Getter와 Setter 메소드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Quiz getQuiz() { // Quiz 엔티티 참조를 반환하는 Getter
        return quiz;
    }

    public void setQuiz(Quiz quiz) { // Quiz 엔티티 참조를 설정하는 Setter
        this.quiz = quiz;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QuizResponse{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", quiz=" + quiz + // Quiz 참조 출력
                ", userAnswer='" + userAnswer + '\'' +
                ", isCorrect=" + isCorrect +
                ", score=" + score +
                '}';
    }
}
