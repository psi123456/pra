package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_id_seq")
    @SequenceGenerator(name = "quiz_id_seq", sequenceName = "quiz_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "options", nullable = true)
    private String options;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "score")
    private Integer score; // 문제의 점수를 저장하는 필드

    @Column(name = "explanation", nullable = true) // 해설 필드 추가
    private String explanation; // 정답에 대한 해설

    public Quiz() {
        // 기본 생성자
    }

    public Quiz(String questionText, String options, String correctAnswer, String difficulty, Integer score, String explanation) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.score = score;
        this.explanation = explanation; // 생성자를 통해 해설도 초기화
    }

    // Getter와 Setter 메소드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", options='" + options + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", score=" + score +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
