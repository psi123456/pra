package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes(@RequestParam(required = false) String questiontext) {
        try {
            List<Quiz> quizzes = new ArrayList<>();

            if (questiontext == null)
                quizRepository.findAll().forEach(quizzes::add);
            else
                quizRepository.findByQuestionTextContaining(questiontext).forEach(quizzes::add);

            if (quizzes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") long id) {
        Optional<Quiz> quizData = quizRepository.findById(id);

        return quizData.map(quiz -> new ResponseEntity<>(quiz, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        try {
            Quiz _quiz = quizRepository
                    .save(new Quiz(quiz.getQuestionText(), quiz.getOptions(), quiz.getCorrectAnswer(), quiz.getDifficulty(), quiz.getScore(), quiz.getExplanation()));
            return new ResponseEntity<>(_quiz, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable("id") long id,@RequestBody Quiz quiz) {
        Optional<Quiz> quizData = quizRepository.findById(id);

        if (quizData.isPresent()) {
            Quiz _quiz = quizData.get();
            _quiz.setQuestionText(quiz.getQuestionText());
            _quiz.setOptions(quiz.getOptions());
            _quiz.setCorrectAnswer(quiz.getCorrectAnswer());
            _quiz.setDifficulty(quiz.getDifficulty());
            _quiz.setScore(quiz.getScore()); // 점수 설정 추가
            _quiz.setExplanation(quiz.getExplanation()); // 점수 설정 추가
            return new ResponseEntity<>(quizRepository.save(_quiz), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable("id") long id) {
        try {
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/quizzes")
    public ResponseEntity<HttpStatus> deleteAllQuizzes() {
        try {
            quizRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}